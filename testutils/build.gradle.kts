@file:Suppress("UnstableApiUsage")
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("myapp.android.library")
    id("myapp.jetbrains.kotlin.android")
}

android {
    namespace = "ch.com.findrealestate.testutils"
}

dependencies {

    api(libs.mockk)
    api(libs.mockk.android)
    api(libs.kotlinx.coroutines.test)
    api(libs.junit4)
    api(libs.androidx.test.ext)
    api(libs.robolectric)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
