package com.app.barcodescanner.scanner.presentation.model


sealed class ScannerActions {
    data class CameraPermissionChanged(val isGranted: Boolean) : ScannerActions()
    data class BarcodeScanned(val barcode: String) : ScannerActions()
}
