package com.seiko.kroute.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

interface TabScreenItem {

    val route: String

    @Composable
    fun Content(navController: NavController)
}