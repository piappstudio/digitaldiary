package com.piappstudio.digitaldiary.di

import com.piappstudio.digitaldiary.ui.welcome.SplashViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModules = module {
    singleOf(::SplashViewModel)
}