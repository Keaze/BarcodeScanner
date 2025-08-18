package com.app.core.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    object Home : Routes

    @Serializable
    object Scanner : Routes

    @Serializable
    data class Details(val barcodeId: Int?) : Routes
}