package com.seiko.kroute.sample.setting

import com.dimension.maskbook.common.routeProcessor.annotations.Route

@Suppress("CONST_VAL_WITHOUT_INITIALIZER")
@Route
expect object SettingRoute {
    const val Tab: String
}
