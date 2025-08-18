package com.app.barcodescanner.scanner.presentation.screens.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.presentation.model.ScanResultUi
import com.app.barcodescanner.scanner.presentation.model.ScannerActions
import com.app.barcodescanner.scanner.presentation.screens.overview.components.ScanOverviewBottomBar
import com.app.barcodescanner.scanner.presentation.screens.overview.components.ScanResultList
import com.app.barcodescanner.scanner.presentation.screens.overview.components.ScannerSettingsDrawer
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
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ScannerSettingsDrawer(
                modifier = modifier,
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
                androidx.compose.material3.CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Barcode Scanner",
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