package com.seiko.kroute.sample.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.seiko.kroute.common.TabScreenItem
import com.seiko.kroute.sample.labs.LabsTabScreenItem
import com.seiko.kroute.sample.persona.PersonaTabScreenItem
import com.seiko.kroute.sample.setting.SettingTabScreenItem
import com.seiko.kroute.sample.wallet.WalletTabScreenItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScene(
    navController: NavController,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val tabs = remember {
        listOf(
            PersonaTabScreenItem,
            WalletTabScreenItem,
            LabsTabScreenItem,
            SettingTabScreenItem,
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                tabs.forEachIndexed { index, tab ->
                    BottomNavigationItem(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        icon = {
                            Icon(tab.icon, contentDescription = null)
                        }
                    )
                }
            }
        }
    ) {
        HorizontalPager(
            count = tabs.size,
            state = pagerState,
        ) { index ->
            tabs[index].Content(navController)
        }
    }
}

private val TabScreenItem.icon: ImageVector
    get() = when (this) {
        PersonaTabScreenItem -> Icons.Default.Person
        WalletTabScreenItem -> Icons.Default.AccountBalanceWallet
        LabsTabScreenItem -> Icons.Default.Apps
        else -> Icons.Default.Settings
    }
