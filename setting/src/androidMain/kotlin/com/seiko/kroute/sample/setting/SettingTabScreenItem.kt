package com.seiko.kroute.sample.setting

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.seiko.kroute.common.TabScreenItem
import com.seiko.kroute.sample.setting.ui.SettingTabScreen

object SettingTabScreenItem : TabScreenItem {
    override val route: String = SettingRoute.Tab
    @Composable
    override fun Content(navController: NavController) {
        SettingTabScreen(navController)
    }
}
