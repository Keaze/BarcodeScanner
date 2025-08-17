package com.app.barcodescanner.scanner.presentation.overview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.app.barcodescanner.scanner.presentation.overview.components.ScanOverviewBottomBar
import com.app.barcodescanner.scanner.presentation.overview.components.ScannerOverview
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme

@Composable
fun ScannerOverviewScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { ScanOverviewBottomBar(modifier) }
    ) { innerPadding ->
        ScannerOverview(modifier = modifier.padding(innerPadding))
    }
}


@Composable
@PreviewLightDark
private fun ScannerOverviewScreenPreview() {
    BarcodeScannerTheme {
        ScannerOverviewScreen()
    }
}