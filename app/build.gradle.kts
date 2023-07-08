@file:Suppress("UnstableApiUsage")
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("myapp.android.application")
    id("myapp.jetbrains.kotlin.android")
    id("myapp.android.compose")
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
}

android {

    namespace = "ch.com.findrealestate"

    detekt {
        toolVersion = "1.23.0"
        config = files("$rootDir/config/detekt/detekt.yml")
    }

}

dependencies {
    //val composeBom = platform(libs.androidx.compose.bom)
    //implementation(composeBom) no need here as they already added in build logic configureAndroidCompose
    implementation(libs.bundles.composeBomList)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose) // provide hilt view model used in compose navigation

    // coil
    implementation(libs.coil.compose)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // chrome tab
    implementation(libs.androidx.browser)

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation (project(":flow-redux"))

    testImplementation(project(":testutils"))
    testImplementation(libs.junit4)
    testImplementation(libs.kotlinTestJunit5)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
