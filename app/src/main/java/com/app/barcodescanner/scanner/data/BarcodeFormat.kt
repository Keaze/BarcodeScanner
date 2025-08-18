package com.app.barcodescanner.scanner.data

import kotlinx.serialization.Serializable

@Serializable
enum class BarcodeFormat(val id: Int) {
    Unknown(-1),
    Code128(1),
    Code39(2),
    Code93(4),
    Codabar(8),
    DataMatrix(16),
    Ean13(32),
    Ean8(64),
    ITF(128),
    QR_CODE(256),
    UpcA(512),
    UpcE(1024),
    Pdf417(2048),
    Aztec(4096);

    companion object {
        fun toBarcodeFormat(id: Int): BarcodeFormat {
            return entries.firstOrNull { it.id == id } ?: Unknown
        }
    }
}

