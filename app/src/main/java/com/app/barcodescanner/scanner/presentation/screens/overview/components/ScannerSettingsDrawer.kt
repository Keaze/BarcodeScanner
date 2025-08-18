package com.app.barcodescanner.scanner.presentation.screens.overview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.barcodescanner.scanner.data.BarcodeFormat
import com.app.barcodescanner.scanner.presentation.model.ScannerActions
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerSettingsDrawer(
    modifier: Modifier = Modifier,
    selectedFormats: Set<BarcodeFormat> = BarcodeFormat.entries.toSet(),
    fnc1: String = "",
    gs: String = "",
    onAction: (ScannerActions) -> Unit = {},
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 32.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Settings",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Barcode Formats",
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = CenterVertically
        ) {
            Button(
                onClick = { onAction(ScannerActions.SelectAllFormats) },
                modifier = Modifier.defaultMinSize(minWidth = 0.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = "Select All")
            }
            Button(
                onClick = { onAction(ScannerActions.DeselectAllFormats) },
                modifier = Modifier.defaultMinSize(minWidth = 0.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = "Deselect All")
            }
        }
        BarcodeFormat.entries.toSet().forEach { format ->
            val checked = selectedFormats.contains(format)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAction(ScannerActions.ToggleFormat(format, !checked)) }
                    .padding(vertical = 0.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = CenterVertically,
            ) {
                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { enabled ->
                            onAction(ScannerActions.ToggleFormat(format, enabled))
                        },
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = format.name,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .padding(vertical = 1.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "FNC1",
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = fnc1,
            onValueChange = { onAction(ScannerActions.UpdateFnc1(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text(text = "Enter FNC1") },
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "GS",
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = gs,
            onValueChange = { onAction(ScannerActions.UpdateGs(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text(text = "Enter GS") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onAction(ScannerActions.ResetSettings) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Reset",
                fontSize = 18.sp
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ScannerSettingsDrawerPreview() {
    BarcodeScannerTheme {
        ScannerSettingsDrawer(
            fnc1 = "1",
            gs = "2"
        )
    }
}