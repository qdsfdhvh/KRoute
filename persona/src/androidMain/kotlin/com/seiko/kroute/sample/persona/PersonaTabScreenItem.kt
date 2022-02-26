package com.seiko.kroute.sample.persona

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.seiko.kroute.common.TabScreenItem
import com.seiko.kroute.sample.persona.ui.PersonaTabScreen

object PersonaTabScreenItem : TabScreenItem {
    override val route: String = PersonaRoute.Tab
    @Composable
    override fun Content(navController: NavController) {
        PersonaTabScreen(navController)
    }
}