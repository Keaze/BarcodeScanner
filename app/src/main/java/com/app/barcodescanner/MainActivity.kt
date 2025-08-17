package com.app.barcodescanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.barcodescanner.scanner.presentation.camera.BarcodeScannerApp
import com.app.barcodescanner.scanner.presentation.overview.ScannerOverviewScreen
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme
import com.app.core.navigation.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarcodeScannerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.Home) {
                    composable<Routes.Home> {
                        ScannerOverviewScreen(onStartScan = { navController.navigate(Routes.Scanner) })
                    }
                    composable<Routes.Scanner> {
                        BarcodeScannerApp(
                            onBarcodeScanned = {
                                println("Barcode scanned: $it")
                                navController.popBackStack()
                            },
                            onStopScanning = navController::popBackStack
                        )
                    }
                }
            }
        }
    }
}