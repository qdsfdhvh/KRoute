package com.seiko.kroute.sample.labs.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dimension.maskbook.common.routeProcessor.annotations.Back
import com.dimension.maskbook.common.routeProcessor.annotations.NavGraphDestination
import com.seiko.kroute.common.DeepLinks
import com.seiko.kroute.sample.labs.LabsRoute

@NavGraphDestination(
    route = LabsRoute.Swap,
    deepLinks = [DeepLinks.Labs.Swap],
)
@Composable
fun LabsSwapScene(
    @Back onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Labs")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text("Labs Swap")
        }
    }
}