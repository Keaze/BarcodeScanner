package com.app.barcodescanner.scanner.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class BarcodeValuesUi(val name: String, val value: String)

fun GS1Values.toUi(): BarcodeValuesUi {
    return BarcodeValuesUi(name = aiName, value = aiValue)
}