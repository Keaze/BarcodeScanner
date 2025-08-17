package com.app.barcodescanner.scanner.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class BarcodeScannerViewModel : ViewModel() {
    private val _state = MutableStateFlow(BarcodeScannerState())
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
        }
    }

}


sealed class ScannerActions {
    data class CameraPermissionChanged(val isGranted: Boolean) : ScannerActions()
}