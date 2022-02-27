package com.seiko.kroute.sample.persona.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dimension.maskbook.common.routeProcessor.annotations.Back
import com.dimension.maskbook.common.routeProcessor.annotations.NavGraphDestination
import com.seiko.kroute.common.navigationComposeDialog
import com.seiko.kroute.common.navigationComposeDialogPackage
import com.seiko.kroute.sample.persona.PersonaRoute

@NavGraphDestination(
    route = PersonaRoute.Dialog,
    packageName = navigationComposeDialogPackage,
    functionName = navigationComposeDialog,
)
@Composable
fun PersonaDialog(
    @Back onBack: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onBack,
        title = {
            Text("Persona Dialog")
        },
        text = {
            Text("this is dialog")
        },
        buttons = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                onClick = onBack
            ) {
                Text("ok")
            }
        }
    )
}