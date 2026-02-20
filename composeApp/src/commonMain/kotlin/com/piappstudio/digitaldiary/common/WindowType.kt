package com.piappstudio.digitaldiary.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

enum class WindowType {
    Compact,
    Medium,
    Expanded
}

@Composable
fun rememberWindowType(): WindowType {
    val width = LocalWindowInfo.current.containerDpSize.width
    return when {
        width < 600.dp -> WindowType.Compact
        width < 840.dp -> WindowType.Medium
        else -> WindowType.Expanded
    }
}
