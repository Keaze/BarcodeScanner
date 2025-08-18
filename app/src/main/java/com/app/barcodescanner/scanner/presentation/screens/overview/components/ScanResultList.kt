package com.app.barcodescanner.scanner.presentation.screens.overview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.presentation.model.ScanResultUi
import com.app.barcodescanner.scanner.presentation.model.ScannerActions
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme

@Composable
fun ScanResultList(
    modifier: Modifier = Modifier,
    scanResults: List<ScanResultUi> = emptyList(),
    onAction: (ScannerActions) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Scan Result:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = { onAction(ScannerActions.CleanList) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Clear Result")
        }
        // Make the list scroll within the content area
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(scanResults) { barcode ->
                ResultCard(
                    scanResult = barcode.barcodeCleaned,
                    barcodeType = barcode.barcodeType,
                    onAction = onAction,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun ScanResultListPreview() {
    BarcodeScannerTheme {
        ScanResultList(
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
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}