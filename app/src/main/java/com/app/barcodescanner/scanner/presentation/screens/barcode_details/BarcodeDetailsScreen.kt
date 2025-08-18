package com.app.barcodescanner.scanner.presentation.screens.barcode_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.presentation.model.ScanResultUi
import com.app.barcodescanner.scanner.presentation.model.ScannerActions
import com.app.barcodescanner.scanner.presentation.model.getBarcodeValues
import com.app.barcodescanner.scanner.presentation.screens.barcode_details.components.BarcodeDetailBottomBar
import com.app.barcodescanner.scanner.presentation.screens.barcode_details.components.BarcodeDetailValueList
import com.app.barcodescanner.scanner.presentation.screens.barcode_details.components.BarcodeInfo
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme

@Composable
fun BarcodeDetailsScreen(
    barcode: ScanResultUi?,
    modifier: Modifier = Modifier,
    onAction: (ScannerActions) -> Unit = {}
) {
    BackHandler {
        onAction(ScannerActions.ToOverview)
    }
    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Barcode Details",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            }
        },
        bottomBar = { BarcodeDetailBottomBar(modifier, onAction) }
    ) { innerPadding ->

        if (barcode != null) {
            SelectionContainer(Modifier.fillMaxSize()) {
                Column(modifier.padding(innerPadding)) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        Column {
                            BarcodeInfo("Format", barcode.barcodeType)
                            BarcodeInfo("Raw", barcode.barcodeRaw)
                            BarcodeInfo("Clean", barcode.barcodeCleaned)
                        }
                    }
                    BarcodeDetailValueList(barcode.getBarcodeValues())
                }
            }
        } else {
            Text(text = "No barcode selected")
        }
    }
}

@Composable
@PreviewLightDark
private fun BarcodeDetailsScreenPreview() {
    BarcodeScannerTheme {
        BarcodeDetailsScreen(
            barcode = ScanResultUi(
                barcodeType = BarcodeFormat.Code128.name,
                barcodeRaw = "Test",
                barcodeCleaned = "Test"
            )
        )
    }
}

