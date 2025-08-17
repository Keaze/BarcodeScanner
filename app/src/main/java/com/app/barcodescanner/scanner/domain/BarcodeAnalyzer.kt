package com.app.barcodescanner.scanner.domain

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(
    private val onBarcodeScanned: (String) -> Unit,
    private val barcodeFormats: List<Int> = emptyList()
) : ImageAnalysis.Analyzer {

    // Configure the scanner to only look for the formats listed in barcodeFormats
    private val scanner by lazy {
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
                        (barcodeFormats.isEmpty() || barcode.format in barcodeFormats) &&
                                barcode.rawValue != null
                    }
                    ?.rawValue

                value?.let(onBarcodeScanned)
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
