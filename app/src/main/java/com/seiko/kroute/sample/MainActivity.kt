package com.seiko.kroute.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.seiko.kroute.common.AppRoute
import com.seiko.kroute.common.ModuleSetup
import com.seiko.kroute.sample.labs.LabsModuleSetup
import com.seiko.kroute.sample.ui.generatedRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Route()
            }
        }
    }
}

@Composable
fun Route() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = AppRoute.Home) {
        generatedRoute(navController)
        LabsModuleSetup.route(this, navController)
    }
}

fun ModuleSetup.route(
    builder: NavGraphBuilder,
    navController: NavController
) = builder.route(navController)
