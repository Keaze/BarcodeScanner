package com.app.barcodescanner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.barcodescanner.scanner.presentation.model.BarcodeScannerViewModel
import com.app.barcodescanner.scanner.presentation.model.ScannerActions
import com.app.barcodescanner.scanner.presentation.model.ScannerActions.CameraPermissionChanged
import com.app.barcodescanner.scanner.presentation.screens.camera.ScannerScreen
import com.app.barcodescanner.scanner.presentation.screens.overview.ScannerOverviewScreen
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme
import com.app.core.navigation.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val hasCameraPermission = checkCameraPermission(context)
            val viewModel = viewModel<BarcodeScannerViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            viewModel.onAction(CameraPermissionChanged(hasCameraPermission))

            BarcodeScannerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.Home) {
                    composable<Routes.Home> {
                        ScannerOverviewScreen(
                            hasCameraPermission = state.cameraPermission,
                            onPermissionChange = viewModel::onAction,
                            onStartScan = { navController.navigate(Routes.Scanner) })
                    }
                    composable<Routes.Scanner> {
                        ScannerScreen(
                            onBarcodeScanned = {
                                viewModel.onAction(ScannerActions.BarcodeScanned(it))
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

@Composable
private fun checkCameraPermission(context: Context): Boolean = ContextCompat.checkSelfPermission(
    context,
    Manifest.permission.CAMERA
) == PackageManager.PERMISSION_GRANTED