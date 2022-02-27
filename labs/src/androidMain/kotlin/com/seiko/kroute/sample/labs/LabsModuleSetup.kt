package com.seiko.kroute.sample.labs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.seiko.kroute.common.ModuleSetup

object LabsModuleSetup : ModuleSetup {
    override fun NavGraphBuilder.route(navController: NavController) {
        generatedLabsRoute(navController)
    }
}