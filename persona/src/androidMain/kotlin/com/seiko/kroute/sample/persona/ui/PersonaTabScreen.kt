package com.seiko.kroute.sample.persona.ui

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.seiko.kroute.common.DeepLinks
import com.seiko.kroute.sample.persona.PersonaRoute

@Composable
fun PersonaTabScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Persona")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = "Persona Tab")
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = {
                    navController.navigate(PersonaRoute.Dialog)
                }
            ) {
                Text("Open Dialog")
            }
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = {
                    navController.navigate(
                        Uri.parse(DeepLinks.Labs.Swap)
                    )
                }
            ) {
                Text("Go to Labs Swap")
            }
        }
    }
}
