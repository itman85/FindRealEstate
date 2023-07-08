plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin{
    plugins {
        register("androidApplication") {
            id = "myapp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidKotlin") {
            id = "myapp.jetbrains.kotlin.android"
            implementationClass = "AndroidKotlinConventionPlugin"
        }
        register("androidLibrary") {
            id = "myapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "myapp.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
    }
}
