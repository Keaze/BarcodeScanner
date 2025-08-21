package com.app.barcodescanner.scanner.presentation.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.data.ScanResult
import com.app.barcodescanner.scanner.domain.BarcodeAnalyzer
import com.app.barcodescanner.scanner.domain.GS1Parser
import com.app.barcodescanner.scanner.peristance.BarcodeScannerStateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarcodeScannerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BarcodeScannerStateRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        savedStateHandle.get<BarcodeScannerState>("barcode_scanner_state") ?: BarcodeScannerState()
    )
    val state: StateFlow<BarcodeScannerState> = _state.asStateFlow()
    val imageAnalyzer = BarcodeAnalyzer()
    val parser = GS1Parser()

    init {
        viewModelScope.launch {
            repository.getAppState().collect { persistentState ->
                if (persistentState != null && _state.value == BarcodeScannerState()) {
                    updateAppState { persistentState }
                }
            }
        }
    }


    fun onAction(action: ScannerActions) {
        when (action) {
            is ScannerActions.CameraPermissionChanged -> {
//                updateAppState { it.copy(cameraPermission = action.isGranted) }
            }

            is ScannerActions.BarcodeScanned -> addBarcodeToHistory(action.barcode)
            is ScannerActions.CleanList -> onCleanList()

            is ScannerActions.StartScan -> imageAnalyzer.reset()
            is ScannerActions.ToOverview -> Unit
            is ScannerActions.OnBarcodeClick -> Unit
            is ScannerActions.ToggleFormat -> toggleSelectedFormat(action.format, action.enabled)
            is ScannerActions.SelectAllFormats -> updateAppState {
                it.copy(
                    selectedFormats = BarcodeFormat.entries.toSet()
                )
            }

            is ScannerActions.DeselectAllFormats -> updateAppState { it.copy(selectedFormats = emptySet()) }

            is ScannerActions.UpdateFnc1 -> updateAppState { it.copy(fnc1 = action.value) }
            is ScannerActions.UpdateGs -> updateAppState { it.copy(gs = action.value) }

            is ScannerActions.ResetSettings -> updateAppState {
                it.copy(
                    selectedFormats = BarcodeFormat.entries.toSet(),
                    fnc1 = FNC1_DEFAULT,
                    gs = GS_DEFAULT,
                )
            }
        }


    }

    private fun toggleSelectedFormat(format: BarcodeFormat, enabled: Boolean) {
        val current = _state.value.selectedFormats.toMutableSet()
        if (enabled) current.add(format) else current.remove(format)
        updateAppState { it.copy(selectedFormats = current) }
    }

    private fun onCleanList() {
        updateAppState { it.copy(scannedBarcodes = emptyList()) }
    }

    private fun updateAppState(update: (BarcodeScannerState) -> BarcodeScannerState) {
        val newState = update(_state.value)
        _state.value = newState
        savedStateHandle["barcode_scanner_state"] = newState

        // Also save to persistent storage
        viewModelScope.launch {
            repository.saveAppState(newState)
        }
    }

    private fun addBarcodeToHistory(scanResult: ScanResult) {
        if (scanResult.isValid.not()) {
            //TODO Add Error Message
            return
        }
        updateAppState { it.copy(scannedBarcodes = it.scannedBarcodes + scanResult) }
    }

    fun getImageAnalyzer(onScanComplete: (String) -> Unit) = imageAnalyzer.apply {
        barcodeFormats = _state.value.selectedFormats
        // Note: Analyzer's internal format configuration is static after initialization in current implementation.
        onBarcodeScanned = { scanResult ->
            addBarcodeToHistory(scanResult)
            onScanComplete(scanResult.id.toString())
        }
    }
}