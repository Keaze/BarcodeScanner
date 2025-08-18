package com.app.barcodescanner.scanner.presentation.screens.barcode_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.barcodescanner.scanner.presentation.screens.barcode_details.shared.TextWithCopyButton
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme

@Composable
fun BarcodeValueEntry(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier.padding(5.dp)) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
        ) {
            Column(
                modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = "$title: ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextWithCopyButton(value = value)
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun BarcodeValueEntryPreview() {
    BarcodeScannerTheme {
        BarcodeValueEntry(title = "Title", value = "Test")
    }
}