package com.piappstudio.digitaldiary.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
sealed interface PiRoute: NavKey {
    @Serializable
    data object Splash : PiRoute

    @Serializable
    data object Diary : PiRoute

    @Serializable
    data class DiaryDetail(val eventId: Long) : PiRoute

    @Serializable
    data object Reminder : PiRoute
    @Serializable
    data object Settings : PiRoute
}
// Creates the required serializing configuration for open polymorphism


val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(PiRoute.Diary::class, PiRoute.Diary.serializer())
            subclass(PiRoute.DiaryDetail::class, PiRoute.DiaryDetail.serializer())
            subclass(PiRoute.Reminder::class, PiRoute.Reminder.serializer())
            subclass(PiRoute.Settings::class, PiRoute.Settings.serializer())
            subclass(PiRoute.Splash::class, PiRoute.Splash.serializer())
        }
    }
}