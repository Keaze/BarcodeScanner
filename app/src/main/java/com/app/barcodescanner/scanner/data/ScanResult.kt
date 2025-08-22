package com.app.barcodescanner.scanner.data

import android.os.Parcelable
import com.app.barcodescanner.scanner.presentation.model.GS1ParseErrorUI
import com.app.barcodescanner.scanner.presentation.model.GS1Values
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class ScanResult(
    val id: UUID = UUID.randomUUID(),
    val barcodeFormat: BarcodeFormat,
    val rawValue: String,
    val cleaned: String,
    val parseError: GS1ParseErrorUI? = null,
    val parseResult: List<GS1Values> = emptyList(),
) :
    Parcelable {
    val isValid
        get() = rawValue.isNotBlank()
}