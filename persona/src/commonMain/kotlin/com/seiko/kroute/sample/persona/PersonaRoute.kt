package com.seiko.kroute.sample.persona

import com.dimension.maskbook.common.routeProcessor.annotations.Route

@Suppress("CONST_VAL_WITHOUT_INITIALIZER")
@Route
expect object PersonaRoute {
    const val Tab: String
    const val Dialog: String
}
