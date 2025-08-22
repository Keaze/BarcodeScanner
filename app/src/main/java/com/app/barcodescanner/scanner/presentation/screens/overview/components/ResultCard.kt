package com.app.barcodescanner.scanner.presentation.screens.overview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.barcodescanner.scanner.presentation.model.ScannerActions
import com.app.barcodescanner.scanner.presentation.screens.shared.TextWithCopyButton
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme

@Composable
fun ResultCard(
    modifier: Modifier = Modifier,
    barcodeId: String = "",
    scanResult: String = "",
    barcodeType: String = "",
    onAction: (ScannerActions) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        onClick = { onAction(ScannerActions.OnBarcodeClick(barcodeId)) }
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = barcodeType,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            TextWithCopyButton(
                scanResult, Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(8.dp),
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                style = LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }

}


@PreviewLightDark
@Composable
private fun ResultCardPreview() {
    BarcodeScannerTheme {
        ResultCard(
            scanResult = "TTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestest",
//            scanResult = "Test",
            barcodeType = "GS1"
        )
    }
}
