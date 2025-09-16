package com.app.barcodescanner.scanner.presentation.screens.shared

import android.app.Activity
import android.content.Context

fun exitApp(context: Context, background: Boolean = false) {
    val activity = context as? Activity ?: return
    if (background) {
        activity.moveTaskToBack(true)
    } else {
        activity.finishAndRemoveTask()
    }
}
