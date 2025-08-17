package com.app.barcodescanner.scanner.presentation.model


data class BarcodeScannerState(
    val scannedBarcodes: List<ScanResultUi> = emptyList(),
    val cameraPermission: Boolean = false
)