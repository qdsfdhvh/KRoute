package com.seiko.kroute.common

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface ModuleSetup {
    fun NavGraphBuilder.route(navController: NavController)
}
