package com.app.barcodescanner.scanner.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScanResult(val barcodeFormat: BarcodeFormat, val rawValue: String, val cleaned: String) :
    Parcelable {
    val isValid
        get() = rawValue.isNotBlank()
}