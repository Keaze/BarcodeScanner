package com.app.barcodescanner.scanner.presentation.model

data class ScanResultUi(
    val barcodeType: String,
    val barcodeRaw: String,
    val barcodeCleanned: String
)