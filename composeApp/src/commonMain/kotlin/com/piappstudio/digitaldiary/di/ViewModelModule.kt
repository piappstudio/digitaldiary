package com.piappstudio.digitaldiary.di

import com.piappstudio.digitaldiary.ui.welcome.SplashViewModel
import com.piappstudio.digitaldiary.ui.diary.DiaryDashboardViewModel
import com.piappstudio.digitaldiary.ui.diary.detail.DiaryDetailViewModel
import com.piappstudio.digitaldiary.ui.reminder.ReminderDashboardViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * ViewModels module for dependency injection
 *
 * - singleOf: Single instance for app-wide services (SplashViewModel)
 * - factoryOf: New instance per screen (Dashboard & Detail ViewModels)
 *
 * Repositories are injected from SharedModule as singletons
 */
val viewModelModules = module {
    // Splash Screen - Single instance (needed once at startup)
    singleOf(::SplashViewModel)

    // Diary ViewModels - Factory instances (new instance per screen)
    // DiaryRepository is automatically injected
    factoryOf(::DiaryDashboardViewModel)
    factoryOf(::DiaryDetailViewModel)

    // Reminder ViewModels - Factory instances (new instance per screen)
    // ReminderRepository is automatically injected
    factoryOf(::ReminderDashboardViewModel)
}