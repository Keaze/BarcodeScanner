package com.app.barcodescanner.scanner.presentation.screens.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.barcodescanner.scanner.presentation.model.ScannerActions
import com.app.barcodescanner.scanner.presentation.screens.overview.components.ScanOverviewBottomBar
import com.app.barcodescanner.scanner.presentation.screens.overview.components.ScanResultList
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme

@Composable
fun ScannerOverviewScreen(
    modifier: Modifier = Modifier,
    scanResults: List<String> = emptyList(),
    hasCameraPermission: Boolean = false,
    onStartScan: () -> Unit = {},
    onPermissionChange: (ScannerActions.CameraPermissionChanged) -> Unit = {},
) {


    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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
                onStartScan,
                onPermissionChange,
            )
        }) { innerPadding ->

            // Scan Result Display
        if (scanResults.isNotEmpty()) {
            ScanResultList(modifier = Modifier.padding(innerPadding), scanResults = scanResults)
            }
        }
    }

@Composable
@PreviewLightDark
private fun ScannerOverviewScreenPreview() {
    BarcodeScannerTheme {
        ScannerOverviewScreen(
            hasCameraPermission = true,
            onStartScan = {},
            onPermissionChange = {},
            scanResults = listOf(
                "Scan result 1Scan result 1Scan result 1Scan result 1Scan result 1",
                "Scan result 2",
                "Scan result 2",
                "Scan result 2",
                "Scan result 2",
                "Scan result 2",
                "Scan result 3"
            )
        )
    }
}
