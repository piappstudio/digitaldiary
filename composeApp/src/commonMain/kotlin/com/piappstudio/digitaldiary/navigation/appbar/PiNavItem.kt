package com.piappstudio.digitaldiary.navigation.appbar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.ui.graphics.vector.ImageVector
import com.piappstudio.digitaldiary.navigation.PiRoute

data class PiNavItem(
    val icon: ImageVector,
    val title: String
)

val bottomNavItems =  mapOf(
    PiRoute.Diary to PiNavItem(
        icon = Icons.Default.Event,
        title = "Diary"
    ),
    PiRoute.Reminder to PiNavItem(
        icon = Icons.Default.Timeline,
        title = "Reminder"
    ),
    PiRoute.Settings to PiNavItem(
        icon = Icons.Default.Settings,
        title = "Settings"
    )


)