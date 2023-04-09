@file:Suppress("UnstableApiUsage")
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("myapp.android.library")
    id("myapp.jetbrains.kotlin.android")
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.kotlin.kapt)
}

android {


    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://private-9f1bb1-homegate3.apiary-mock.com/\"")
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {

    // Retrofit
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.okhttp3.logging.interceptor)

    // Room db
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    testImplementation(libs.room.testing)

    implementation(project(":domain"))

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.junit4)
    implementation(project(":testutils"))
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
