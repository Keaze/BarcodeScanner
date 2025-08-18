package com.app.barcodescanner.scanner.presentation.model

import com.app.barcodescanner.scanner.data.BarcodeFormat

data class ScanResultUi(
    val barcodeType: BarcodeFormat,
    val barcodeRaw: String,
    val barcodeCleaned: String
)