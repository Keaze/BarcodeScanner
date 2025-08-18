package com.app.barcodescanner.scanner.presentation.model

import com.app.barcodescanner.scanner.data.ScanResult


data class BarcodeScannerState(
    val scannedBarcodes: List<ScanResult> = emptyList(),
    val cameraPermission: Boolean = false
) {
    val lastScannedBarcode
        get() = scannedBarcodes.lastOrNull()?.toUi(scannedBarcodes.size - 1)
    val scannedBarcodesHistory
        get() = scannedBarcodes.mapIndexed { index, scanResult -> scanResult.toUi(index) }
            .reversed()

    fun getBarcodeWithIndex(index: Int): ScanResultUi? {
        return scannedBarcodes.getOrNull(index)?.toUi(index)
    }
}