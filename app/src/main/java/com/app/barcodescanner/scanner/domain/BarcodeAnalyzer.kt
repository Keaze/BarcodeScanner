package com.app.barcodescanner.scanner.domain

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(
    private val onBarcodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val barcodeFormats = emptyList<Int>()

    // Configure the scanner to only look for the formats listed in barcodeFormats
    private val scanner = run {
        if (barcodeFormats.isNotEmpty()) {
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    barcodeFormats.first(),
                    *barcodeFormats.drop(1).toIntArray()
                )
                .build()
            BarcodeScanning.getClient(options)
        } else {
            // Fallback to default if no formats specified (no restriction)
            BarcodeScanning.getClient()
        }
    }
    private var isProcessing = false

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        if (isProcessing) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            isProcessing = true
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        // If no specific formats are configured, accept any detected barcode.
                        // Otherwise, only handle allowed formats.
                        val formatAllowed = barcodeFormats.isEmpty() || (barcode.format in barcodeFormats)
                        if (formatAllowed) {
                            barcode.rawValue?.let { barcodeValue ->
                                onBarcodeScanned(barcodeValue)
                                break
                            }
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
        } else {
            imageProxy.close()
        }
    }
}
