package com.app.barcodescanner.scanner.presentation.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme

@Composable
fun ScannerOverview(modifier: Modifier = Modifier) {
    val scanResult = "Scan result"
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Scanner Overview", modifier = Modifier)
        Text(text = scanResult, modifier = modifier)
    }
}


@Composable
@PreviewLightDark
private fun ScannerOverviewPreview() {
    BarcodeScannerTheme {
        ScannerOverview()
    }
}