@file:Suppress("UnstableApiUsage")

import ch.com.findrealestate.unitTestDependencies

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("myapp.android.library")
    id("myapp.jetbrains.kotlin.android")
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ch.com.findrealestate.data"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://private-9f1bb1-homegate3.apiary-mock.com/\"")
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
    ksp(libs.room.compiler) // use ksp instead of kapt to fix the issue when compile Room with kotlin 1.9.0 or higher
    testImplementation(libs.room.testing)

    implementation(project(":domain"))

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // unit test dependencies
    unitTestDependencies(project)
}
