package com.piappstudio.digitaldiary.di

import android.content.Context
import com.piappstudio.digitaldiary.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Initialize Koin with Android-specific configuration.
 * This should be called from the Application class onCreate() method.
 *
 * Usage: In your custom Application class:
 * ```
 * override fun onCreate() {
 *     super.onCreate()
 *     initKoinAndroid(this)
 * }
 * ```
 */
@Suppress("unused")
fun initKoinAndroid(context: Context) {
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(context)
            modules(commonModule)
        }
    }
}
