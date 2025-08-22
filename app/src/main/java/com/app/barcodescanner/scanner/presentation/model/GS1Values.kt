package com.app.barcodescanner.scanner.presentation.model

import android.os.Parcelable
import com.app.barcodescanner.scanner.peristance.Gs1AiStore
import kotlinx.parcelize.Parcelize
import org.app.gs1parser.data.AiValue

@Parcelize
data class GS1Values(val aiName: String, val aiId: String, val aiValue: String) : Parcelable


fun AiValue.toUi(): GS1Values {
    return GS1Values(
        aiName = ai.description,
        aiId = ai.id,
        aiValue = value
    )
}

fun GS1Values.toStore(): Gs1AiStore {
    return Gs1AiStore(
        aiName = aiName,
        aiId = aiId,
        aiValue = aiValue
    )
}

fun Gs1AiStore.toData(): GS1Values {
    return GS1Values(
        aiName = aiName,
        aiId = aiId,
        aiValue = aiValue
    )
}