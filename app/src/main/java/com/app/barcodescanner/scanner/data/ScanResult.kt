package com.app.barcodescanner.scanner.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class ScanResult(
    val id: UUID = UUID.randomUUID(),
    val barcodeFormat: BarcodeFormat,
    val rawValue: String,
    val cleaned: String
) :
    Parcelable {
    val isValid
        get() = rawValue.isNotBlank()
}