package com.app.barcodescanner.scanner.presentation.model

import com.app.barcodescanner.scanner.data.ScanResult


sealed class ScannerActions {
    data class CameraPermissionChanged(val isGranted: Boolean) : ScannerActions()
    data class BarcodeScanned(val barcode: ScanResult) : ScannerActions()
    object StartScan : ScannerActions()
    object CleanList : ScannerActions()
}
