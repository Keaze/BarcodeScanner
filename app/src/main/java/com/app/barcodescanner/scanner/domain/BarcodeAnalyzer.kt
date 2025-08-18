package com.app.barcodescanner.scanner.domain

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.data.ScanResult
import com.app.barcodescanner.scanner.presentation.model.FNC1_DEFAULT
import com.app.barcodescanner.scanner.presentation.model.GS_DEFAULT
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.UUID

private const val GROUP_SEPARATOR_STRING = 29.toChar().toString()

class BarcodeAnalyzer(
    var onBarcodeScanned: (ScanResult) -> Unit = {},
    var barcodeFormats: Set<BarcodeFormat> = emptySet(),
    var fnc1: String = FNC1_DEFAULT,
    var gs: String = GS_DEFAULT
) : ImageAnalysis.Analyzer {

    fun reset() {
        hasScanned = false
        isProcessing = false
    }

    fun getBarcodeFromatInts(): IntArray {
        return barcodeFormats.map { it.id }.toIntArray()
    }
    // Configure the scanner to only look for the formats listed in barcodeFormats
    private fun getScanner(): BarcodeScanner {
        val barcodeFormatInts = getBarcodeFromatInts()
        if (barcodeFormatInts.isNotEmpty()) {
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    barcodeFormatInts.first(),
                    *barcodeFormatInts.drop(1).toIntArray()
                )
                .build()
            return BarcodeScanning.getClient(options)
        } else {
            // Fallback to default if no formats specified (no restriction)
            return BarcodeScanning.getClient()
        }
    }

    private var isProcessing = false
    private var hasScanned = false

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        if (isProcessing || hasScanned) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        isProcessing = true
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        getScanner().process(image)
            .addOnSuccessListener { barcodes ->
                val value = barcodes
                    .firstOrNull { barcode ->
                        // If no specific formats are configured, accept any detected barcode.
                        // Otherwise, only handle allowed formats.
                        (barcodeFormats.isEmpty() || barcode.format in getBarcodeFromatInts()) &&
                                barcode.rawValue != null
                    }
                    ?.run {
                        val rawWithVisibleGS = rawValue!!.replace(GROUP_SEPARATOR_STRING, "<GS>")
                        val raw =
                            if (rawValue!!.startsWith(fnc1.substring(0, 1))) rawValue!!.substring(
                                fnc1.length
                            ) else rawValue!!
                        ScanResult(
                            id = UUID.randomUUID(),
                            BarcodeFormat.toBarcodeFormat(this.format),
                            rawWithVisibleGS,
                            raw
                        )
                    }

                value?.let {
                    if (!hasScanned) {
                        hasScanned = true
                        onBarcodeScanned(it)
                    }
                }
            }
            .addOnFailureListener {
                // Handle any errors
            }
            .addOnCompleteListener {
                isProcessing = false
                imageProxy.close()
            }
    }
}
