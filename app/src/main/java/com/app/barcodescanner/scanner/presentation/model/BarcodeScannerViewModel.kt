package com.app.barcodescanner.scanner.presentation.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.data.ScanResult
import com.app.barcodescanner.scanner.domain.BarcodeAnalyzer
import com.app.barcodescanner.scanner.domain.BarcodeAnalyzerFactory
import com.app.barcodescanner.scanner.domain.GS1ParserFactory
import com.app.barcodescanner.scanner.peristance.BarcodeScannerStateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.app.gs1parser.GS1Scanner
import org.app.utils.ResultKt
import javax.inject.Inject

@HiltViewModel
class BarcodeScannerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BarcodeScannerStateRepository,
    private val parserFactory: GS1ParserFactory,
    private val analyzerFactory: BarcodeAnalyzerFactory
) : ViewModel() {

    private val _state = MutableStateFlow(
        savedStateHandle.get<BarcodeScannerState>("barcode_scanner_state") ?: BarcodeScannerState()
    )
    val state: StateFlow<BarcodeScannerState> = _state.asStateFlow()
    val imageAnalyzer = BarcodeAnalyzer()
    var parser: GS1Scanner? = null

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

            is ScannerActions.UpdateFnc1 -> {
                updateAppState { it.copy(fnc1 = action.value) }
                updateParser()
            }

            is ScannerActions.UpdateGs -> {
                updateAppState { it.copy(gs = action.value) }
                updateParser()
            }

            is ScannerActions.ResetSettings -> {
                updateAppState {
                    it.copy(
                        selectedFormats = BarcodeFormat.entries.toSet(),
                        fnc1 = FNC1_DEFAULT,
                        gs = GS_DEFAULT,
                    )
                }
                updateParser()
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
            return
        }
        val parser = getCurrentParser()
        val resultWithParsing = if (parser.isGs1Format(scanResult.rawValue)) {
            parseGs1Barcode(parser, scanResult)
        } else {
            scanResult
        }
        updateAppState { it.copy(scannedBarcodes = it.scannedBarcodes + resultWithParsing) }
    }

    private fun getCurrentParser(): GS1Scanner {
        if (parser == null) {
            updateParser()
        }
        return parser!!
    }

    private fun updateParser() {
        parser = parserFactory.getParser(fnc1 = _state.value.fnc1, gs = _state.value.gs).orThrow()
    }

    private fun parseGs1Barcode(parser: GS1Scanner, scanResult: ScanResult): ScanResult {
        val result = parser.parse(scanResult.rawValue)
        return when (result) {
            is ResultKt.Success -> {
                result.value.barcodeValues.map { it.toUi() }
                    .let { scanResult.copy(parseResult = it) }
            }

            is ResultKt.Failure -> {
                result.error.ui().let { scanResult.copy(parseError = it) }
            }
        }
    }

    fun getImageAnalyzer(onScanComplete: (String) -> Unit) = analyzerFactory.createAnalyzer(
        barcodeFormats = _state.value.selectedFormats,
        onBarcodeScanned = { scanResult ->
            addBarcodeToHistory(scanResult)
            onScanComplete(scanResult.id.toString())
        }
    )
}