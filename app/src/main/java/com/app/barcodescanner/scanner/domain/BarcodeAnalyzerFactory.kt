package com.app.barcodescanner.scanner.domain

import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.data.ScanResult

class BarcodeAnalyzerFactory() {
    fun createAnalyzer(
        barcodeFormats: Set<BarcodeFormat>,
        onBarcodeScanned: (ScanResult) -> Unit
    ): BarcodeAnalyzer {
        return BarcodeAnalyzer(
            barcodeFormats = barcodeFormats,
            onBarcodeScanned = onBarcodeScanned
        )
    }
}