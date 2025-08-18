package com.app.barcodescanner.scanner.data

data class ScanResult(val barcodeFormat: BarcodeFormat, val rawValue: String, val cleaned: String) {
    val isValid
        get() = rawValue.isNotBlank()
}