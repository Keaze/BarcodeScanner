package com.app.barcodescanner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.app.barcodescanner.scanner.presentation.model.BarcodeScannerViewModel
import com.app.barcodescanner.scanner.presentation.model.ScannerActions
import com.app.barcodescanner.scanner.presentation.model.ScannerActions.CameraPermissionChanged
import com.app.barcodescanner.scanner.presentation.screens.barcode_details.BarcodeDetailsScreen
import com.app.barcodescanner.scanner.presentation.screens.camera.ScannerScreen
import com.app.barcodescanner.scanner.presentation.screens.overview.ScannerOverviewScreen
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme
import com.app.core.navigation.Routes

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
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
                            scanResults = state.scannedBarcodesHistory,
                            hasCameraPermission = state.cameraPermission,
                            selectedFormats = state.selectedFormats,
                            fnc1 = state.fnc1,
                            gs = state.gs,
                            onAction = {
                                viewModel.onAction(it)
                                when (it) {
                                    is ScannerActions.StartScan -> navController.navigate(Routes.Scanner)
                                    is ScannerActions.OnBarcodeClick -> navController.navigate(
                                        Routes.Details(it.barcodeId)
                                    )
                                    else -> {}
                                }
                            })
                    }
                    composable<Routes.Scanner> {
                        ScannerScreen(
                            analyzer = viewModel.getImageAnalyzer { index ->
                                navController.navigate(Routes.Details(index))
                            },
                            onStopScanning = navController::popBackStack
                        )
                    }
                    composable<Routes.Details> { backStackEntry ->
                        val details = backStackEntry.toRoute<Routes.Details>()
                        BarcodeDetailsScreen(
                            barcode = state.getBarcodeWithIndex(details.barcodeId),
                            onAction = {
                                viewModel.onAction(it)
                                when (it) {
                                    is ScannerActions.ToOverview -> navController.navigate(Routes.Home) {
                                        popUpTo(
                                            Routes.Home
                                        ) { inclusive = true }
                                    }

                                    else -> {}
                                }
                            }
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