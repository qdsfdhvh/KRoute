package com.seiko.kroute.sample.labs

import com.dimension.maskbook.common.routeProcessor.annotations.Route

@Suppress("CONST_VAL_WITHOUT_INITIALIZER")
@Route
expect object LabsRoute {
    const val Tab: String
    object Detail {
        operator fun invoke(id: String): String
    }
    const val Swap: String
}
