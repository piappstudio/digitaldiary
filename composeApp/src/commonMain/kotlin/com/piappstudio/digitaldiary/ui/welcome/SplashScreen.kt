package com.piappstudio.digitaldiary.ui.welcome

import KottieAnimation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piappstudio.digitaldiary.common.theme.Dimens
import com.piappstudio.digitaldiary.common.theme.DigitalDiaryTheme
import com.piappstudio.digitaldiary.common.theme.DiaryMood
import com.piappstudio.digitaldiary.common.theme.rememberThemePreferences
import digitaldiary.composeapp.generated.resources.Res
import digitaldiary.composeapp.generated.resources.app_name
import digitaldiary.composeapp.generated.resources.splash_copyright
import digitaldiary.composeapp.generated.resources.splash_subtitle
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.stringResource

@Preview
@Composable
fun SplashScreen() {
    // Create theme preferences and set LOVE mood
    val themePreferences = rememberThemePreferences()

    LaunchedEffect(Unit) {
        //themePreferences.setMood(DiaryMood.LOVE)
    }

    DigitalDiaryTheme(themePreferences = themePreferences, darkMode = false, diaryMood = DiaryMood.PASSIONATE) {
        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                var animation by remember { mutableStateOf("") }

                LaunchedEffect(Unit){
                    animation = Res.readBytes("files/notes.json").decodeToString()
                }

                var playing by remember { mutableStateOf(false) }

                val composition = rememberKottieComposition(
                    spec = KottieCompositionSpec.File(animation) // Or KottieCompositionSpec.Url || KottieCompositionSpec.JsonString
                )
                val animationState by animateKottieCompositionAsState(
                    composition = composition,
                )

                KottieAnimation(
                    composition = composition,
                    progress = { animationState.progress },
                    modifier = Modifier.size(300.dp).align(Alignment.Center)
                )

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.space)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = Dimens.bigger_space),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(Res.string.app_name),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = Dimens.space)
                    )
                    Text(
                        stringResource(Res.string.splash_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = Dimens.half_space)
                    )
                    Text(
                        stringResource(Res.string.splash_copyright),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
