package com.app.barcodescanner.scanner.presentation.screens.overview.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.barcodescanner.R
import com.app.barcodescanner.scanner.presentation.model.ScannerActions
import com.app.barcodescanner.ui.theme.BarcodeScannerTheme

@Composable
fun ScanOverviewBottomBar(
    modifier: Modifier = Modifier,
    hasCameraPermission: Boolean = true,
    onAction: (ScannerActions) -> Unit = {},
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onAction(ScannerActions.CameraPermissionChanged(isGranted))
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (hasCameraPermission) {
                    onAction(ScannerActions.StartScan)
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = if (hasCameraPermission) stringResource(R.string.start_scanning) else stringResource(
                    R.string.grant_camera_permission
                ),
                fontSize = 18.sp
            )
        }

        if (!hasCameraPermission) {
            Text(
                text = stringResource(R.string.camera_permission_required),
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
    }

}


@PreviewLightDark
@Composable
private fun ScanOverviewBottomBarPreview() {
    BarcodeScannerTheme {
        ScanOverviewBottomBar(hasCameraPermission = true)
    }
}