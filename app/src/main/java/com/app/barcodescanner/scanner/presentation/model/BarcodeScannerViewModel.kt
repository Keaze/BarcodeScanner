package com.app.barcodescanner.scanner.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.barcodescanner.scanner.data.ScanResult
import com.app.barcodescanner.scanner.domain.BarcodeAnalyzer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class BarcodeScannerViewModel : ViewModel() {
    private val _state = MutableStateFlow(BarcodeScannerState())
    private val imageAnalyzer = BarcodeAnalyzer()
    val state = _state.onStart {}
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            BarcodeScannerState()
        )

    fun onAction(action: ScannerActions) {
        when (action) {
            is ScannerActions.CameraPermissionChanged -> {
                _state.value = _state.value.copy(cameraPermission = action.isGranted)
            }
            is ScannerActions.BarcodeScanned -> addBarcodeToHistory(action.barcode)
            is ScannerActions.CleanList -> _state.value =
                _state.value.copy(scannedBarcodes = emptyList())

            is ScannerActions.StartScan -> imageAnalyzer.reset()
        }
    }

    private fun addBarcodeToHistory(scanResult: ScanResult) {
        if (scanResult.isValid.not()) {
            //TODO Add Error Message
            return
        }
        _state.value =
            _state.value.copy(scannedBarcodes = _state.value.scannedBarcodes + scanResult)
    }

    fun getImageAnalyzer(onScanComplete: () -> Unit) = imageAnalyzer.apply {
        barcodeFormats
        onBarcodeScanned = ::addBarcodeToHistory
        afterScanAction = onScanComplete
    }
}