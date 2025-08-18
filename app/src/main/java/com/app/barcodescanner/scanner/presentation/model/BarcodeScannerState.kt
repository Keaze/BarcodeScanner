package com.app.barcodescanner.scanner.presentation.model

import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.data.ScanResult


const val FNC1_DEFAULT = "]C1"
const val GS_DEFAULT = "<GS>"

data class BarcodeScannerState(
    val scannedBarcodes: List<ScanResult> = emptyList(),
    val cameraPermission: Boolean = false,
    val selectedFormats: Set<BarcodeFormat> = BarcodeFormat.entries.filterNot { it == BarcodeFormat.Unknown }
        .toSet(),
    val fnc1: String = FNC1_DEFAULT,
    val gs: String = GS_DEFAULT
) {
    val lastScannedBarcode
        get() = scannedBarcodes.lastOrNull()?.toUi(scannedBarcodes.size - 1)
    val scannedBarcodesHistory
        get() = scannedBarcodes.mapIndexed { index, scanResult -> scanResult.toUi(index) }
            .reversed()

    fun getBarcodeWithIndex(index: Int?): ScanResultUi? {
        index ?: return null
        return scannedBarcodes.getOrNull(index)?.toUi(index)
    }
}