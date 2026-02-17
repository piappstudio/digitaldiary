package com.piappstudio.digitaldiary.navigation

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
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.piappstudio.digitaldiary.common.WindowType
import com.piappstudio.digitaldiary.common.rememberWindowType
import com.piappstudio.digitaldiary.navigation.appbar.PiAppBottomBar
import com.piappstudio.digitaldiary.navigation.appbar.PiAppNavigationRail
import com.piappstudio.digitaldiary.ui.diary.AddDiaryScreen
import com.piappstudio.digitaldiary.ui.diary.DiaryDashboardScreen
import com.piappstudio.digitaldiary.ui.diary.detail.DiaryDetailScreen
import com.piappstudio.digitaldiary.ui.reminder.ReminderDashboardScreen
import com.piappstudio.digitaldiary.ui.setting.SettingsDashboardScreen
import com.piappstudio.digitaldiary.ui.welcome.SplashScreen

@Composable
fun SetUpNavigationRoot() {

    val appBackStack = rememberNavBackStack(config, PiRoute.Splash)

    val windowType = rememberWindowType()
    val useRail = windowType != WindowType.Compact


    Scaffold (bottomBar = {
        if (!useRail) {
            PiAppBottomBar(backStack = appBackStack)
        } }) {

        Row (modifier = Modifier.padding(it)) {
            if (useRail) {
                PiAppNavigationRail(backStack = appBackStack, modifier = Modifier.padding(it))
            }
            Box (modifier = Modifier.fillMaxSize()) {
                NavDisplay(backStack = appBackStack, entryProvider = entryProvider {
                    entry<PiRoute.Diary> {
                        DiaryDashboardScreen(onNavigateDetail = { eventId ->
                            appBackStack.add(PiRoute.DiaryDetail(eventId))
                        })

                    }
                    entry<PiRoute.DiaryDetail> { route ->
                        DiaryDetailScreen(
                            eventId = route.eventId,
                            onBackClick = { appBackStack.removeLast() },
                            onNavigateEdit = { eventId ->
                                appBackStack.add(PiRoute.AddDiary(eventId))
                            }
                        )
                    }
                    entry<PiRoute.AddDiary> { route ->
                        AddDiaryScreen(
                            eventId = route.eventId,
                            onBack = { appBackStack.removeLast() }
                        )
                    }
                    entry<PiRoute.Reminder> {
                        ReminderDashboardScreen()

                    }
                    entry<PiRoute.Settings> {
                        SettingsDashboardScreen()
                    }
                    entry<PiRoute.Splash> {
                        SplashScreen {
                            appBackStack.clear()
                            appBackStack.add(PiRoute.Diary)
                        }
                    }
                })
            }
        }

    }
}