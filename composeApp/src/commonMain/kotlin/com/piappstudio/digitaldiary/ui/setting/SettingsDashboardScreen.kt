package com.piappstudio.digitaldiary.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.piappstudio.digitaldiary.ui.component.PiHeader

@Composable
fun SettingsDashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        PiHeader(
            title = "Settings",
            subtitle = "Customize your diary experience"
        )
    }
}
