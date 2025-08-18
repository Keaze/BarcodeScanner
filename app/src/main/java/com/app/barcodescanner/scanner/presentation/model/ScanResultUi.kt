package com.app.barcodescanner.scanner.presentation.model

import com.app.barcodescanner.scanner.data.ScanResult
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ScanResultUi(
    val barcodeType: String,
    val barcodeRaw: String,
    val barcodeCleaned: String,
    val id: String = UUID.randomUUID().toString(),
)


fun ScanResult.toUi(): ScanResultUi {
    // Make non-printable GS (Group Separator, ASCII 29) visible in the cleaned representation
    return ScanResultUi(
        id = id.toString(),
        barcodeType = barcodeFormat.name,
        barcodeRaw = rawValue,
        barcodeCleaned = cleaned
    )
}

fun ScanResultUi.getBarcodeValues(): List<BarcodeValuesUi> {
    return listOf()
}