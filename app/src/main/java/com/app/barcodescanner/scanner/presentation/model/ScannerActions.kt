package com.app.barcodescanner.scanner.presentation.model

import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.data.ScanResult


sealed class ScannerActions {
    data class CameraPermissionChanged(val isGranted: Boolean) : ScannerActions()
    data class BarcodeScanned(val barcode: ScanResult) : ScannerActions()
    object ToOverview : ScannerActions()
    object StartScan : ScannerActions()
    object CleanList : ScannerActions()
    object ResetSettings : ScannerActions()
    data class OnBarcodeClick(val barcodeId: Int) : ScannerActions()

    // Settings / Drawer actions
    data class ToggleFormat(val format: BarcodeFormat, val enabled: Boolean) : ScannerActions()
    object SelectAllFormats : ScannerActions()
    object DeselectAllFormats : ScannerActions()
    data class UpdateFnc1(val value: String) : ScannerActions()
    data class UpdateGs(val value: String) : ScannerActions()
}
