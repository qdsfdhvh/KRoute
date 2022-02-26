package com.seiko.kroute.sample.labs.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dimension.maskbook.common.routeProcessor.annotations.Back
import com.dimension.maskbook.common.routeProcessor.annotations.NavGraphDestination
import com.dimension.maskbook.common.routeProcessor.annotations.Path
import com.seiko.kroute.common.ui.widget.TopAppBackBar
import com.seiko.kroute.sample.labs.LabsRoute

@NavGraphDestination(
    route = LabsRoute.Detail.path,
)
@Composable
fun LabsDetailScene(
    @Back onBack: () -> Unit,
    @Path("id") id: String,
) {
    Scaffold(
        topBar = {
            TopAppBackBar(
                onBack = onBack,
                title = {
                    Text("Labs")
                },
            )
        }
    ) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text("Labs Detail id=$id")
        }
    }
}
