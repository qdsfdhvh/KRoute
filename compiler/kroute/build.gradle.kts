plugins {
    kotlin("jvm")
}

dependencies {
    implementation(projects.compiler.kroute.annotations)
    implementation("com.google.devtools.ksp:symbol-processing-api:${Versions.ksp}")
    implementation("com.squareup:kotlinpoet:${Versions.kotlinpoet}")
    implementation("com.squareup:kotlinpoet-ksp:${Versions.kotlinpoet}")
}
