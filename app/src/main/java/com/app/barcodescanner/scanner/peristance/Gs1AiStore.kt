package com.app.barcodescanner.scanner.peristance

import kotlinx.serialization.Serializable

@Serializable
data class Gs1AiStore(val aiName: String, val aiId: String, val aiValue: String)
