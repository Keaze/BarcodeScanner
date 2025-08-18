package com.app.barcodescanner.scanner.presentation.model

import com.app.barcodescanner.scanner.data.ScanResult


data class BarcodeScannerState(
    val scannedBarcodes: List<ScanResult> = emptyList(),
    val cameraPermission: Boolean = false
) {
    val lastScannedBarcode
        get() = scannedBarcodes.lastOrNull()
    val scannedBarcodesHistory
        get() = scannedBarcodes.map { ScanResultUi(it.barcodeFormat, it.rawValue, it.rawValue) }
            .reversed()
}