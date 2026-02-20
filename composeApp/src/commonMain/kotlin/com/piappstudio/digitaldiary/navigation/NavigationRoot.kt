package com.piappstudio.digitaldiary.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.piappstudio.digitaldiary.navigation.appbar.BottomNavGraph
import com.piappstudio.digitaldiary.ui.diary.detail.DiaryDetailScreen
import com.piappstudio.digitaldiary.ui.diary.edit.AddDiaryScreen
import com.piappstudio.digitaldiary.ui.reminder.detail.ReminderDetailScreen
import com.piappstudio.digitaldiary.ui.reminder.edit.AddReminderScreen
import com.piappstudio.digitaldiary.ui.welcome.SplashScreen

@Composable
fun SetUpNavigationRoot() {

    val appBackStack = rememberNavBackStack(config, PiRoute.Splash)

    Box(modifier = Modifier.fillMaxSize()) {
        NavDisplay(backStack = appBackStack, entryProvider = entryProvider {

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

            entry<PiRoute.ReminderDetail> { route ->
                ReminderDetailScreen(
                    reminderId = route.reminderId,
                    onBackClick = { appBackStack.removeLast() },
                    onNavigateEdit = { reminderId ->
                        appBackStack.add(PiRoute.AddReminder(reminderId))
                    }
                )
            }
            entry<PiRoute.AddReminder> { route ->
                AddReminderScreen(
                    reminderId = route.reminderId,
                    onBack = { appBackStack.removeLast() }
                )
            }
            entry <PiRoute.BottomBarScreen> {
                BottomNavGraph(appNavBackStack = appBackStack)
            }
            entry<PiRoute.Splash> {
                SplashScreen {
                    appBackStack.clear()
                    appBackStack.add(PiRoute.BottomBarScreen)
                }
            }
        })
    }
}
