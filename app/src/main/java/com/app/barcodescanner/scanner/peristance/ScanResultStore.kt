package com.app.barcodescanner.scanner.peristance

import com.app.barcodescanner.scanner.data.BarcodeFormat
import kotlinx.serialization.Serializable

@Serializable
class ScanResultStore(
    val barcodeFormat: BarcodeFormat,
    val rawValue: String,
    val cleaned: String,
    val parseError: String?,
    val parseResult: List<Gs1AiStore>
)