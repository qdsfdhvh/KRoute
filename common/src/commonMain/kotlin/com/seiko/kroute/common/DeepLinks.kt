package com.seiko.kroute.common

import com.dimension.maskbook.common.routeProcessor.annotations.Route

@Suppress("CONST_VAL_WITHOUT_INITIALIZER")
@Route(schema = "kroute")
expect object DeepLinks {
    object Labs {
        const val Swap: String
    }
}
