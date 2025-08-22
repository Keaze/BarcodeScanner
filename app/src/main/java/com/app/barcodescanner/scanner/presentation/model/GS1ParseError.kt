package com.app.barcodescanner.scanner.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.app.gs1parser.data.Gs1ParseErrors

@Parcelize
data class GS1ParseErrorUI(val message: String) : Parcelable

fun Gs1ParseErrors.ui(): GS1ParseErrorUI {
    return when (this) {
        is Gs1ParseErrors.AiNotFound -> GS1ParseErrorUI(
            message = "No Application Identifier (AI) found in barcode: ${this.barcode}"
        )

        is Gs1ParseErrors.NotAGs1Barcode -> GS1ParseErrorUI(
            message = "The scanned code is not a GS1 barcode"
        )

        is Gs1ParseErrors.ValueLengthError -> GS1ParseErrorUI(
            message = "Invalid value length for AI ${this.aiId}. Expected length: ${this.aiLength}. Barcode: ${this.barcode}"
        )

        is Gs1ParseErrors.GsNotFound -> GS1ParseErrorUI(
            message = "Group separator (GS) not found after AI ${this.aiId} in barcode: ${this.barcode}"
        )
    }
}