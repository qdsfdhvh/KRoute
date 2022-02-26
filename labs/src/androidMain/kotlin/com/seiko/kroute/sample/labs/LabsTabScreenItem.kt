package com.seiko.kroute.sample.labs

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.seiko.kroute.common.TabScreenItem
import com.seiko.kroute.sample.labs.ui.LabsTabScreen

object LabsTabScreenItem : TabScreenItem {
    override val route: String = LabsRoute.Tab
    @Composable
    override fun Content(navController: NavController) {
        LabsTabScreen(navController)
    }
}
