package com.piappstudio.digitaldiary.navigation.appbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.piappstudio.digitaldiary.common.WindowType
import com.piappstudio.digitaldiary.common.rememberWindowType
import com.piappstudio.digitaldiary.navigation.PiRoute
import com.piappstudio.digitaldiary.navigation.bottomConfig
import com.piappstudio.digitaldiary.ui.diary.DiaryDashboardScreen
import com.piappstudio.digitaldiary.ui.reminder.ReminderDashboardScreen
import com.piappstudio.digitaldiary.ui.setting.SettingsDashboardScreen

@Composable
fun BottomNavGraph(appNavBackStack: NavBackStack<NavKey>) {

    val bottomNavBackStack = rememberNavBackStack(bottomConfig, PiRoute.BottomBar.Diary)
    val windowType = rememberWindowType()
    val useRail = windowType != WindowType.Compact

    Scaffold (bottomBar = {
        if (!useRail) {
            PiAppBottomBar(backStack = bottomNavBackStack)
        }
    },  contentWindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)) {

        Row (modifier = Modifier.padding(it)) {
            if (useRail) {
                PiAppNavigationRail(backStack = bottomNavBackStack, modifier = Modifier.padding(it))
            }
            Box (modifier = Modifier.fillMaxSize()) {
                NavDisplay(backStack = bottomNavBackStack, entryProvider = entryProvider {
                    entry<PiRoute.BottomBar.Diary> {
                        DiaryDashboardScreen(onNavigateDetail = { eventId ->
                            appNavBackStack.add(PiRoute.DiaryDetail(eventId))
                        }, onNavigateAdd = {
                            appNavBackStack.add(PiRoute.AddDiary())
                        })
                    }
                    entry<PiRoute.BottomBar.Reminder> {
                        ReminderDashboardScreen(
                            onNavigateDetail = { reminderId ->
                                appNavBackStack.add(PiRoute.ReminderDetail(reminderId))
                            },
                            onNavigateAdd = {
                                appNavBackStack.add(PiRoute.AddReminder())
                            }
                        )

                    }
                    entry<PiRoute.BottomBar.Settings> {
                        SettingsDashboardScreen()
                    }
                })
            }
        }

    }
}