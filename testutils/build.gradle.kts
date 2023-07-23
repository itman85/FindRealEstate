@file:Suppress("UnstableApiUsage")
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("myapp.android.library")
    id("myapp.jetbrains.kotlin.android")
    id("myapp.android.compose")
}

android {
    namespace = "ch.com.findrealestate.testutils"
}

dependencies {
    // these dependencies can be referenced in both unit test and ui test
    implementation(libs.mockk)
    implementation(libs.mockk.android)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit4)
    implementation(libs.androidx.test.ext)
    implementation(libs.kotlinTestJunit5)
    implementation(libs.turbine)
    implementation(libs.androidx.test.espresso.core)
    implementation(libs.bundles.testComposeBomList)
    implementation(libs.hilt.android.test)
}
