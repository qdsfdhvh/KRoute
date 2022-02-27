package com.seiko.kroute.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seiko.kroute.common.AppRoute
import com.seiko.kroute.sample.labs.generatedLabsRoute
import com.seiko.kroute.sample.persona.generatedPersonaRoute
import com.seiko.kroute.sample.ui.HomeScene

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
        composable(AppRoute.Home) { HomeScene(navController) }
        generatedLabsRoute(navController)
        generatedPersonaRoute(navController)
    }
}
