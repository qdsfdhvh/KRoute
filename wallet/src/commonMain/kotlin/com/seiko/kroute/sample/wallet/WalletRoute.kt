package com.seiko.kroute.sample.wallet

import com.dimension.maskbook.common.routeProcessor.annotations.Route

@Suppress("CONST_VAL_WITHOUT_INITIALIZER")
@Route
expect object WalletRoute {
    const val Tab: String
}
