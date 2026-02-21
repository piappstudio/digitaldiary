package com.piappstudio.digitaldiary.navigation.appbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.piappstudio.digitaldiary.common.theme.Dimens

fun NavBackStack<NavKey>.navigateSingleTop(route: NavKey) {
    val existingIndex = indexOf(route)
    if (existingIndex == -1) {
        add(route)
    } else if (existingIndex != size - 1) {
        // Smarter approach to bring existing element to top:
        // Shift all elements above it down by one, then place the target at the top.
        // This avoids removeAt() which would destroy the associated ViewModel.
        val targetRoute = this[existingIndex]
        for (i in existingIndex until size - 1) {
            this[i] = this[i + 1]
        }
        this[size - 1] = targetRoute
    }
}

@Composable
fun PiAppBottomBar(backStack: NavBackStack<NavKey>, modifier: Modifier = Modifier) {
    NavigationBar (modifier = modifier) {
        val current = backStack.lastOrNull()

        bottomNavItems.forEach { (route, item) ->
            NavigationBarItem(
                modifier = Modifier.padding(Dimens.space),
                selected = current == route,
                onClick = {
                    backStack.navigateSingleTop(route)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(item.title)
                }
            )
        }
    }

}

@Composable
fun PiAppNavigationRail(backStack: NavBackStack<NavKey>, modifier: Modifier = Modifier) {
    NavigationRail (modifier = modifier, windowInsets = NavigationRailDefaults.windowInsets) {
        val current = backStack.lastOrNull()

        bottomNavItems.forEach { (route, item) ->
            NavigationRailItem(
                selected = current == route,
                onClick = {
                    backStack.navigateSingleTop(route)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(item.title)
                }
            )
        }
    }

}