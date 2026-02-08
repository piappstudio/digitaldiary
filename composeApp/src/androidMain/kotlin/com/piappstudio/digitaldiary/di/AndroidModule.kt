package com.piappstudio.digitaldiary.di

import android.content.Context
import androidx.activity.ComponentActivity
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Android-specific Koin module that provides platform-specific dependencies.
 * This module should be loaded on Android only to register the Context
 * which is required for database initialization.
 */
val androidModule = module {
    // Context is provided through androidContext() in App.kt when initializing Koin
    // This ensures the Context is available for database operations
}
