plugins {
    id("com.android.application")
    kotlin("android")
}

val accompanist = "0.24.10-beta"
val compose = "1.2.0-beta03"
val coroutinesVersion = "1.6.1"
val composeMaterial3Version = "1.0.0-alpha13"
val nav_version = "2.4.2"

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.lduboscq.minimedia.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta03"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("androidx.compose.ui:ui:$compose")
    implementation("androidx.compose.ui:ui-tooling:$compose")
    implementation("androidx.compose.foundation:foundation:$compose")
    implementation("androidx.compose.material:material:$compose")
    implementation("androidx.compose.material:material-icons-core:$compose")
    implementation("androidx.compose.material:material-icons-extended:$compose")
    implementation("androidx.compose.runtime:runtime-livedata:$compose")
    implementation("androidx.compose.runtime:runtime-rxjava2:$compose")
    implementation("androidx.activity:activity-compose:1.6.0-alpha03")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-rc01")
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.compose.material3:material3:$composeMaterial3Version")
    implementation("com.google.accompanist:accompanist-permissions:$accompanist")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanist")
    implementation("io.coil-kt:coil-compose:2.1.0")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose")
}
