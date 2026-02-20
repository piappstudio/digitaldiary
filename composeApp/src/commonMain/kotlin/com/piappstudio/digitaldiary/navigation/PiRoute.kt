package com.piappstudio.digitaldiary.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
sealed interface PiRoute: NavKey {

    sealed interface BottomBar : PiRoute {
        @Serializable
        data object Diary : BottomBar

        @Serializable
        data object Reminder : BottomBar

        @Serializable
        data object Settings : BottomBar
    }

    @Serializable
    data object Splash : PiRoute

    @Serializable
    data object BottomBarScreen : PiRoute


    @Serializable
    data class DiaryDetail(val eventId: Long) : PiRoute

    @Serializable
    data class AddDiary(val eventId: Long? = null) : PiRoute


    @Serializable
    data class ReminderDetail(val reminderId: Long) : PiRoute

    @Serializable
    data class AddReminder(val reminderId: Long? = null) : PiRoute
// Creates the required serializing configuration for open polymorphism
}

val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(PiRoute.DiaryDetail::class, PiRoute.DiaryDetail.serializer())
            subclass(PiRoute.AddDiary::class, PiRoute.AddDiary.serializer())
            subclass(PiRoute.ReminderDetail::class, PiRoute.ReminderDetail.serializer())
            subclass(PiRoute.AddReminder::class, PiRoute.AddReminder.serializer())
            subclass(PiRoute.Splash::class, PiRoute.Splash.serializer())
        }
    }
}

val bottomConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(PiRoute.BottomBar.Diary::class, PiRoute.BottomBar.Diary.serializer())
            subclass(PiRoute.BottomBar.Reminder::class, PiRoute.BottomBar.Reminder.serializer())
            subclass(PiRoute.BottomBar.Settings::class, PiRoute.BottomBar.Settings.serializer())
        }
    }
}
