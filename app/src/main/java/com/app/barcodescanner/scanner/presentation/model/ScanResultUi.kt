package com.app.barcodescanner.scanner.presentation.model

import com.app.barcodescanner.scanner.data.ScanResult
import kotlinx.serialization.Serializable

@Serializable
data class ScanResultUi(
    val barcodeType: String,
    val barcodeRaw: String,
    val barcodeCleaned: String,
    val id: Int = 0,
)


fun ScanResult.toUi(id: Int = 0): ScanResultUi {
    // Make non-printable GS (Group Separator, ASCII 29) visible in the cleaned representation
    val gsChar = 29.toChar().toString()
    val cleaned = rawValue.replace(gsChar, "<GS>")
    return ScanResultUi(
        id = id,
        barcodeType = barcodeFormat.name,
        barcodeRaw = rawValue,
        barcodeCleaned = cleaned
    )
}

fun ScanResultUi.getBarcodeValues(): List<BarcodeValuesUi> {
    return listOf()
}