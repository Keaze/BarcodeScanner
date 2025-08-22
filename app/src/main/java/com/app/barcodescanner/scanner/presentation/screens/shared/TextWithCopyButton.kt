package com.app.barcodescanner.scanner.presentation.screens.shared

import android.content.ClipData
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.launch

@Composable
fun TextWithCopyButton(
    value: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    val coroutineScope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = value, modifier = modifier.weight(1f), style = style)
        IconButton(onClick = {
            coroutineScope.launch {
                clipboard.setClipEntry(ClipEntry(ClipData.newPlainText(value, value)))
            }
        }) {
            Icon(imageVector = Icons.Filled.ContentCopy, contentDescription = "Copy")
        }
    }
}