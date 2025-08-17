package com.app.barcodescanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.app.barcodescanner.scanner.presentation.camera.BarcodeScannerApp
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarcodeScannerTheme {
                BarcodeScannerApp()
//                ScannerOverviewScreen()
            }
        }
    }
}