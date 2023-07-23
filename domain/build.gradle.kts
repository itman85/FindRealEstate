@file:Suppress("UnstableApiUsage")

import ch.com.findrealestate.unitTestDependencies

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("myapp.android.library")
    id("myapp.jetbrains.kotlin.android")
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "ch.com.findrealestate.domain"
}

dependencies {

    api(libs.kotlinx.coroutines.core)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // unit test dependencies
    unitTestDependencies(project)
}
