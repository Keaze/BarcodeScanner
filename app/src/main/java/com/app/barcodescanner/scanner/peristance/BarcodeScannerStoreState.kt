package com.app.barcodescanner.scanner.peristance

import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.data.ScanResult
import com.app.barcodescanner.scanner.presentation.model.BarcodeScannerState
import com.app.barcodescanner.scanner.presentation.model.FNC1_DEFAULT
import com.app.barcodescanner.scanner.presentation.model.GS1ParseErrorUI
import com.app.barcodescanner.scanner.presentation.model.GS_DEFAULT
import com.app.barcodescanner.scanner.presentation.model.toData
import com.app.barcodescanner.scanner.presentation.model.toStore
import kotlinx.serialization.Serializable


@Serializable
data class BarcodeScannerStoreState(
    val scannedBarcodes: List<ScanResultStore> = emptyList(),
    val selectedFormats: Set<BarcodeFormat> = BarcodeFormat.entries.toSet(),
    val fnc1: String = FNC1_DEFAULT,
    val gs: String = GS_DEFAULT
)

fun BarcodeScannerState.toSerializable(): BarcodeScannerStoreState {
    return BarcodeScannerStoreState(
        scannedBarcodes = this.scannedBarcodes.map { it.toStore() },
        selectedFormats = this.selectedFormats,
        fnc1 = this.fnc1,
        gs = this.gs
    )
}

fun BarcodeScannerStoreState.toAppState(): BarcodeScannerState {
    return BarcodeScannerState(
        scannedBarcodes = this.scannedBarcodes.map { it.toData() },
        selectedFormats = this.selectedFormats,
        fnc1 = this.fnc1,
        gs = this.gs
    )
}

fun ScanResult.toStore() = ScanResultStore(
    barcodeFormat,
    rawValue,
    cleaned,
    parseError?.message,
    parseResult.map { it.toStore() })
fun ScanResultStore.toData() =
    ScanResult(
        barcodeFormat = barcodeFormat,
        rawValue = rawValue,
        cleaned = cleaned,
        parseError = if (parseError == null) null else GS1ParseErrorUI(parseError),
        parseResult = parseResult.map { it.toData() })