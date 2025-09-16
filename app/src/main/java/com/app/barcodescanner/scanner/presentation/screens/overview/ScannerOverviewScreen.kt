package com.app.barcodescanner.scanner.presentation.screens.overview

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.app.barcodescanner.R
import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.presentation.model.ScanResultUi
import com.app.barcodescanner.scanner.presentation.model.ScannerActions
import com.app.barcodescanner.scanner.presentation.screens.overview.components.ScanOverviewBottomBar
import com.app.barcodescanner.scanner.presentation.screens.overview.components.ScanResultList
import com.app.barcodescanner.scanner.presentation.screens.overview.components.ScannerSettingsDrawer
import com.app.barcodescanner.scanner.presentation.screens.shared.exitApp
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme
import kotlinx.coroutines.launch

@Composable
@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
fun ScannerOverviewScreen(
    modifier: Modifier = Modifier,
    scanResults: List<ScanResultUi> = emptyList(),
    hasCameraPermission: Boolean = false,
    selectedFormats: Set<BarcodeFormat> = BarcodeFormat.entries.toSet(),
    fnc1: String = "",
    gs: String = "",
    onAction: (ScannerActions) -> Unit = {},
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showExitDialog by remember { mutableStateOf(false) }
    BackHandler {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            showExitDialog = true
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(stringResource(R.string.exit_app_title)) },
            text = { Text(stringResource(R.string.exit_app_message)) },
            confirmButton = {
                TextButton(onClick = {
                    exitApp(context, background = false)
                }) {
                    Text(stringResource(R.string.exit_app_button))
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(stringResource(R.string.cancel_button))
                }
            }
        )
    }

    ModalNavigationDrawer(
        drawerContent = {
            ScannerSettingsDrawer(
                onAction = onAction,
                selectedFormats = selectedFormats,
                fnc1 = fnc1,
                gs = gs
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.barcode_scanner_title),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    navigationIcon = {
                        Text(
                            text = "☰",
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable { scope.launch { drawerState.open() } }
                        )
                    }
                )
            },
            bottomBar = {
                ScanOverviewBottomBar(
                    modifier,
                    hasCameraPermission,
                    onAction,
                )
            }) { innerPadding ->

            // Scan Result Display
            if (scanResults.isNotEmpty()) {
                SelectionContainer {
                    ScanResultList(
                        modifier = Modifier.padding(innerPadding),
                        scanResults = scanResults,
                        onAction = onAction
                    )
                }
            }
            }
        }
    }

@Composable
@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@PreviewLightDark
private fun ScannerOverviewScreenPreview() {
    BarcodeScannerTheme {
        ScannerOverviewScreen(
            hasCameraPermission = true,
            onAction = {},
            scanResults = listOf(
                ScanResultUi(
                    BarcodeFormat.Code128.name,
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1",
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1"
                ),
                ScanResultUi(
                    BarcodeFormat.Code128.name,
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1",
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1"
                ),
                ScanResultUi(
                    BarcodeFormat.Code128.name,
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1",
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1"
                ),
                ScanResultUi(
                    BarcodeFormat.Code128.name,
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1",
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1"
                ),
                ScanResultUi(
                    BarcodeFormat.Code128.name,
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1",
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1"
                ),
                ScanResultUi(
                    BarcodeFormat.Code128.name,
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1",
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1"
                ),
                ScanResultUi(
                    BarcodeFormat.Code128.name,
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1",
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1"
                ),
                ScanResultUi(
                    BarcodeFormat.Code128.name,
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1",
                    "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1"
                ),
            )
        )
    }
}