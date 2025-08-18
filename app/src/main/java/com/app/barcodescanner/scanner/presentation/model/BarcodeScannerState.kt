package com.app.barcodescanner.scanner.presentation.model

import android.os.Parcelable
import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.data.ScanResult
import kotlinx.parcelize.Parcelize

const val FNC1_DEFAULT = "]C1"
const val GS_DEFAULT = "<GS>"

@Parcelize
data class BarcodeScannerState(
    val scannedBarcodes: List<ScanResult> = emptyList(),
    val selectedFormats: Set<BarcodeFormat> = BarcodeFormat.entries.toSet(),
    val fnc1: String = FNC1_DEFAULT,
    val gs: String = GS_DEFAULT
) : Parcelable {
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