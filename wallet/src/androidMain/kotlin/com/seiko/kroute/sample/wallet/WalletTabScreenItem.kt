package com.seiko.kroute.sample.wallet

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.seiko.kroute.common.TabScreenItem
import com.seiko.kroute.sample.wallet.ui.WalletTabScreen

object WalletTabScreenItem : TabScreenItem {
    override val route: String = WalletRoute.Tab
    @Composable
    override fun Content(navController: NavController) {
        WalletTabScreen(navController)
    }
}
