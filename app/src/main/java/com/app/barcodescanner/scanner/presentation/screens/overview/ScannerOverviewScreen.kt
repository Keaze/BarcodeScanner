package com.app.barcodescanner.scanner.presentation.screens.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.presentation.model.ScanResultUi
import com.app.barcodescanner.scanner.presentation.model.ScannerActions
import com.app.barcodescanner.scanner.presentation.screens.overview.components.ScanOverviewBottomBar
import com.app.barcodescanner.scanner.presentation.screens.overview.components.ScanResultList
import com.app.barcodescanner.scanner.presentation.screens.overview.components.ScannerSettingsDrawer
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme
import kotlinx.coroutines.launch

@Composable
fun ScannerOverviewScreen(
    modifier: Modifier = Modifier,
    scanResults: List<ScanResultUi> = emptyList(),
    hasCameraPermission: Boolean = false,
    selectedFormats: Set<BarcodeFormat> = BarcodeFormat.entries.filterNot { it == BarcodeFormat.Unknown }
        .toSet(),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = "☰",
                        modifier = Modifier
                            .padding(start = 8.dp, top = 32.dp, bottom = 32.dp, end = 16.dp)
                            .clickable { scope.launch { drawerState.open() } },
                    )
                    Text(
                        text = "Barcode Scanner",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 32.dp)
                    )
                }
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