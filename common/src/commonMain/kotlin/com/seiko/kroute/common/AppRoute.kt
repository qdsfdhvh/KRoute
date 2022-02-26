package com.seiko.kroute.common

import com.dimension.maskbook.common.routeProcessor.annotations.Route

@Suppress("CONST_VAL_WITHOUT_INITIALIZER")
@Route
expect object AppRoute {
    const val Home: String
}
