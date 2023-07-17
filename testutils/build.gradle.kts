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
    api(libs.mockk)
    api(libs.mockk.android)
    api(libs.kotlinx.coroutines.test)
    api(libs.junit4)
    api(libs.androidx.test.ext)
    //api(libs.robolectric) robolectric does not work in androidTest, so exclude it
    api(libs.kotlinTestJunit5)
    api(libs.turbine)
    api(libs.androidx.test.espresso.core)
    api(libs.bundles.testComposeBomList)
    api(libs.hilt.android.test)
}
