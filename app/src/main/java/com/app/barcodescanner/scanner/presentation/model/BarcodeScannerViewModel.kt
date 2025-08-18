package com.app.barcodescanner.scanner.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.barcodescanner.scanner.data.BarcodeFormat
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
            is ScannerActions.ToOverview -> Unit
            is ScannerActions.OnBarcodeClick -> Unit
            is ScannerActions.ToggleFormat -> {
                val current = _state.value.selectedFormats.toMutableSet()
                if (action.enabled) current.add(action.format) else current.remove(action.format)
                _state.value = _state.value.copy(selectedFormats = current)
            }

            is ScannerActions.UpdateFnc1 -> {
                _state.value = _state.value.copy(fnc1 = action.value)
            }

            is ScannerActions.UpdateGs -> {
                _state.value = _state.value.copy(gs = action.value)
            }

            is ScannerActions.ResetSettings -> {
                _state.value = _state.value.copy(
                    selectedFormats = BarcodeFormat.entries.filterNot { it == BarcodeFormat.Unknown }
                        .toSet(),
                    fnc1 = FNC1_DEFAULT,
                    gs = GS_DEFAULT,
                )
            }
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

    fun getImageAnalyzer(onScanComplete: (Int) -> Unit) = imageAnalyzer.apply {
        barcodeFormats = _state.value.selectedFormats
        // Note: Analyzer's internal format configuration is static after initialization in current implementation.
        onBarcodeScanned = { scanResult ->
            addBarcodeToHistory(scanResult)
            onScanComplete(_state.value.scannedBarcodes.size - 1)
        }
    }
}