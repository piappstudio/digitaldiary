import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// https://github.com/kotlin-hands-on/get-started-with-cm/blob/main/gradle/libs.versions.toml
//https://github.com/kotlin-hands-on/get-started-with-cm/tree/new-project-structure
plugins {
    alias(libs.plugins.jetbrainKotlinAndroid)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    /*    id("com.google.gms.google-services")
        id("com.google.firebase.crashlytics")*/

}

kotlin {
    target {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    dependencies {

        implementation(project(":composeApp"))

        implementation(libs.compose.uiToolingPreview)
        implementation(libs.androidx.activity.compose)
        /* implementation(project.dependencies.platform(libs.firebase.bom))
         implementation(libs.firebase.crashlytics.ktx)
         implementation(libs.google.firebase.analytics.ktx)
         implementation (libs.firebase.messaging.ktx)
         implementation(libs.firebase.inappmessaging.display.ktx)*/

    }

    android {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        namespace = "com.piappstudio.digitaldiary"

        defaultConfig {
            applicationId = "com.piappstudio.digitaldiary"
            minSdk = libs.versions.android.minSdk.get().toInt()
            targetSdk = libs.versions.android.targetSdk.get().toInt()
            versionCode = 66
            versionName = "2.0"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        buildTypes {
            debug {
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
            release {
                isMinifyEnabled = true
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }
    }
}
