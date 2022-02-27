/*
 *  Mask-Android
 *
 *  Copyright (C) 2022  DimensionDev and Contributors
 *
 *  This file is part of Mask-Android.
 *
 *  Mask-Android is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Mask-Android is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with Mask-Android.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dimension.maskbook.common.routeProcessor

import com.dimension.maskbook.common.routeProcessor.annotations.Back
import com.dimension.maskbook.common.routeProcessor.annotations.GeneratedFunction
import com.dimension.maskbook.common.routeProcessor.annotations.NavGraphDestination
import com.dimension.maskbook.common.routeProcessor.annotations.Navigate
import com.dimension.maskbook.common.routeProcessor.annotations.Path
import com.dimension.maskbook.common.routeProcessor.annotations.Query
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import com.squareup.kotlinpoet.withIndent

private val navControllerType = ClassName("androidx.navigation", "NavController")

private const val argumentsNulNullFormat = "val %N = it.arguments!!.get(%S) as %T"
private const val argumentsNullableFormat = "val %N = it.arguments?.get(%S) as? %T"

@OptIn(KotlinPoetKspPreview::class, KspExperimental::class)
internal class RouteGraphProcessor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation(requireNotNull(NavGraphDestination::class.qualifiedName))
            .filterIsInstance<KSFunctionDeclaration>()

        val generatedFunctionSymbols = resolver
            .getSymbolsWithAnnotation(requireNotNull(GeneratedFunction::class.qualifiedName))
            .filterIsInstance<KSFunctionDeclaration>()

        fun checkValidRoute(symbol: KSFunctionDeclaration): Boolean {
            return try {
                symbol.getAnnotationsByType(NavGraphDestination::class).first().route
                true
            } catch (e: Throwable) {
                false
            }
        }

        if (symbols.any { !checkValidRoute(it) }) {
            return (symbols + generatedFunctionSymbols).toList()
        }

        val actualSymbols = symbols.filter { it.validate() }
        generatedFunctionSymbols.forEach { generatedFunction ->
            generateRoute(actualSymbols.toList(), generatedFunction)
        }
        return symbols.filter { !it.validate() }.toList()
    }

    private fun generateRoute(data: List<KSFunctionDeclaration>, generatedFunction: KSFunctionDeclaration) {
        val dependencies = Dependencies(
            true,
            *(data.mapNotNull { it.containingFile }).toTypedArray()
        )

        val navControllerName = generatedFunction.parameters.find {
            it.type.toTypeName() == navControllerType
        }?.name?.getShortName()
        requireNotNull(navControllerName) { "not find navController in parameters" }

        val packageName = generatedFunction.packageName
        FileSpec.builder(packageName.asString(), "RouteGraph")
            .addImport("androidx.navigation", "NavType")
            .addImport("androidx.navigation", "navDeepLink")
            .addImport("androidx.navigation", "navArgument")
            .also { fileBuilder ->
                fileBuilder.addFunction(
                    FunSpec.builder(generatedFunction.simpleName.getShortName())
                        .addModifiers(KModifier.ACTUAL)
                        .receiver(requireNotNull(generatedFunction.extensionReceiver?.toTypeName()))
                        .addParameter(
                            navControllerName,
                            navControllerType,
                        )
                        .also { builder ->
                            data.forEach { ksFunctionDeclaration ->
                                if (packageName != ksFunctionDeclaration.packageName) {
                                    fileBuilder.addImport(
                                        ksFunctionDeclaration.packageName.asString(),
                                        ksFunctionDeclaration.simpleName.asString()
                                    )
                                }
                                val annotation = ksFunctionDeclaration
                                    .getAnnotationsByType(NavGraphDestination::class)
                                    .first()
                                fileBuilder.addImport(
                                    annotation.packageName,
                                    annotation.functionName
                                )
                                builder.addStatement(
                                    "%N(",
                                    annotation.functionName,
                                )
                                builder.addCode(
                                    buildCodeBlock {
                                        withIndent {
                                            addStatement(
                                                "route = %S,",
                                                annotation.route,
                                            )
                                            val parameters = ksFunctionDeclaration.parameters.filter {
                                                it.isAnnotationPresent(
                                                    Query::class
                                                ) || it.isAnnotationPresent(Path::class)
                                            }
                                            if (parameters.isNotEmpty()) {
                                                addStatement("arguments = listOf(")
                                                withIndent {
                                                    parameters.forEach {
                                                        val type = it.type.resolve()
                                                        val typeName = type.toClassName()

                                                        val argumentName = when {
                                                            it.isAnnotationPresent(Path::class) -> it.getAnnotationsByType(Path::class).first().name
                                                            it.isAnnotationPresent(Query::class) -> it.getAnnotationsByType(Query::class).first().name
                                                            else -> it.name?.asString().orEmpty()
                                                        }

                                                        addStatement(
                                                            "navArgument(%S) { type = NavType.%NType; nullable = %L },",
                                                            argumentName,
                                                            if (typeName.isBoolean) "Bool" else type.declaration.simpleName.asString(),
                                                            it.isAnnotationPresent(Query::class) && !typeName.isLong
                                                        )
                                                    }
                                                }
                                                addStatement("),")
                                            }
                                            if (annotation.deepLinks.isNotEmpty()) {
                                                addStatement("deepLinks = listOf(")
                                                withIndent {
                                                    annotation.deepLinks.forEach {
                                                        addStatement(
                                                            "navDeepLink { uriPattern = %S }",
                                                            it
                                                        )
                                                    }
                                                }
                                                addStatement("),")
                                            }
                                        }
                                    }
                                )
                                builder.beginControlFlow(")")
                                ksFunctionDeclaration.parameters.forEach {
                                    if (it.isAnnotationPresent(Path::class)) {
                                        val path = it.getAnnotationsByType(Path::class).first()
                                        require(path.nullable == it.type.resolve().isMarkedNullable)
                                    }
                                    if (it.isAnnotationPresent(Query::class)) {
                                        require(it.type.resolve().isMarkedNullable)
                                    }
                                    if (it.isAnnotationPresent(Path::class)) {
                                        val path = it.getAnnotationsByType(Path::class).first()
                                        builder.addStatement(
                                            if (path.nullable) argumentsNullableFormat else argumentsNulNullFormat,
                                            it.name?.asString().orEmpty(),
                                            path.name,
                                            it.type.toTypeName()
                                        )
                                    } else if (it.isAnnotationPresent(Query::class)) {
                                        val query = it.getAnnotationsByType(Query::class).first()
                                        builder.addStatement(
                                            argumentsNullableFormat,
                                            it.name?.asString().orEmpty(),
                                            query.name,
                                            it.type.toTypeName()
                                        )
                                    }
                                }
                                builder.addCode(
                                    buildCodeBlock {
                                        addStatement(
                                            "%N(",
                                            ksFunctionDeclaration.simpleName.asString()
                                        )
                                        withIndent {
                                            ksFunctionDeclaration.parameters.forEach {
                                                when {
                                                    it.type.toTypeName() == navControllerType -> {
                                                        addStatement(
                                                            "%N = %N,",
                                                            it.name?.asString() ?: "",
                                                            navControllerName
                                                        )
                                                    }
                                                    it.isAnnotationPresent(Query::class) || it.isAnnotationPresent(Path::class) -> {
                                                        addStatement(
                                                            "%N = %N,",
                                                            it.name?.asString() ?: "",
                                                            it.name?.asString() ?: ""
                                                        )
                                                    }
                                                    it.isAnnotationPresent(Back::class) -> {
                                                        addStatement(
                                                            "%N = { %N.navigateUp() },",
                                                            it.name?.asString() ?: "",
                                                            navControllerName
                                                        )
                                                    }
                                                    it.isAnnotationPresent(Navigate::class) -> {
                                                        val target = it.getAnnotationsByType(Navigate::class).first().target
                                                        val type = it.type.resolve()
                                                        require(type.isFunctionType)
                                                        val declaration = type.declaration as KSClassDeclaration
                                                        val parameters = declaration.getDeclaredFunctions().first().parameters
                                                        val parameter = if (parameters.any()) {
                                                            "\\{(\\w+)}".toRegex().findAll(target).map { it.groups[1]?.value }.joinToString(",") + " ->"
                                                        } else {
                                                            ""
                                                        }
                                                        addStatement(
                                                            "%N = { $parameter %N.navigate(%P) },",
                                                            it.name?.asString() ?: "",
                                                            navControllerName,
                                                            target.replace("{", "\${")
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        addStatement(")")
                                    }
                                )
                                builder.endControlFlow()
                            }
                        }
                        .build()
                )
            }
            .build()
            .writeTo(codeGenerator, dependencies)
    }
}
