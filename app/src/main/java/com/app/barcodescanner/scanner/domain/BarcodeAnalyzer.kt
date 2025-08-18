package com.app.barcodescanner.scanner.domain

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.data.ScanResult
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(
    var onBarcodeScanned: (ScanResult) -> Unit = {},
    var afterScanAction: (() -> Unit) = {},
    var barcodeFormats: List<BarcodeFormat> = emptyList(),
) : ImageAnalysis.Analyzer {

    fun reset() {
        hasScanned = false
        isProcessing = false
    }

    private val barcodeFormatInts = barcodeFormats.map { it.id }
    // Configure the scanner to only look for the formats listed in barcodeFormats
    private val scanner by lazy {
        if (barcodeFormatInts.isNotEmpty()) {
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    barcodeFormatInts.first(),
                    *barcodeFormatInts.drop(1).toIntArray()
                )
                .build()
            BarcodeScanning.getClient(options)
        } else {
            // Fallback to default if no formats specified (no restriction)
            BarcodeScanning.getClient()
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

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                val value = barcodes
                    .firstOrNull { barcode ->
                        // If no specific formats are configured, accept any detected barcode.
                        // Otherwise, only handle allowed formats.
                        (barcodeFormats.isEmpty() || barcode.format in barcodeFormatInts) &&
                                barcode.rawValue != null
                    }
                    ?.run {
                        ScanResult(BarcodeFormat.toBarcodeFormat(this.format), rawValue!!)
                    }

                value?.let {
                    if (!hasScanned) {
                        hasScanned = true
                        onBarcodeScanned(it)
                        afterScanAction()
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
