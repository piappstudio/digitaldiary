package com.piappstudio.digitaldiary.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalWindowInfo

enum class WindowType {
    Compact,
    Medium,
    Expanded
}

@Composable
fun rememberWindowType(): WindowType {
    val width = LocalWindowInfo.current.containerSize.width
    return when {
        width < 600 -> WindowType.Compact
        width < 840 -> WindowType.Medium
        else -> WindowType.Expanded
    }
}
