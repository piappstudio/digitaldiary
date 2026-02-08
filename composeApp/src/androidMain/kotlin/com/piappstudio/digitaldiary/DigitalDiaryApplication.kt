package com.piappstudio.digitaldiary

import android.app.Application
import com.piappstudio.digitaldiary.di.initKoinAndroid

/**
 * Custom Application class for DigitalDiary.
 * Handles initialization of Koin dependency injection with Android context.
 */
class DigitalDiaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Koin with Android context
        initKoinAndroid(this)
    }
}
