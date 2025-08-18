package com.app.barcodescanner.scanner.presentation.screens.barcode_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.app.barcodescanner.scanner.presentation.model.BarcodeValuesUi
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme

@Composable
fun BarcodeDetailValueList(values: List<BarcodeValuesUi>, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.padding(16.dp)) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(values) { value ->
                BarcodeValueEntry(
                    title = value.name,
                    value = value.value
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
fun BarcodeDetailValueListPreview() {
    BarcodeScannerTheme {
        BarcodeDetailValueList(
            values = listOf(
                BarcodeValuesUi(name = "Title 1", value = "Value 1"),
                BarcodeValuesUi(name = "Title 2", value = "Value 2"),
                BarcodeValuesUi(name = "Title 3", value = "Value 3"),
                BarcodeValuesUi(name = "Title 4", value = "Value 4"),
                BarcodeValuesUi(name = "Title 5", value = "Value 5"),
                BarcodeValuesUi(name = "Title 6", value = "Value 6"),
                BarcodeValuesUi(name = "Title 7", value = "Value 7"),

                )
        )
    }
}