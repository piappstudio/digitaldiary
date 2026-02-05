plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
/*    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")*/

}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
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
        }
    }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    namespace = "com.piappstudio.digitaldiary"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.piappstudio.digitaldiary"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 66
        versionName = "2.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
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
dependencies {
    implementation(project(":composeApp"))
}
