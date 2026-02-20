package com.piappstudio.digitaldiary

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.piappstudio.digitaldiary.common.theme.DiaryMood
import com.piappstudio.digitaldiary.common.theme.DigitalDiaryTheme
import com.piappstudio.digitaldiary.di.commonModule
import com.piappstudio.digitaldiary.navigation.SetUpNavigationRoot
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatformTools

@Composable
@Preview
fun App() {

    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        startKoin(appDeclaration = {
            // On Android, context is provided through the Application class
            // On other platforms, we only load the common modules
            modules(commonModule)
        })
    }

    DigitalDiaryTheme (diaryMood = DiaryMood.PASSIONATE) {
        SetUpNavigationRoot()
    }
}