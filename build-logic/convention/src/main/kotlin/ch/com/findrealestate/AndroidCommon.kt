package ch.com.findrealestate

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

@Suppress("UnstableApiUsage")
internal fun Project.configureAndroidCommon(
    extension: CommonExtension<*, *, *, *>,
) {
    extension.apply {
        compileSdk = 33

        defaultConfig {
            minSdk = 26
            vectorDrawables {
                useSupportLibrary = true
            }
            // custom instrumentation test runner with hilt application
            testInstrumentationRunner = "ch.com.findrealestate.runner.UITestRunner"
        }

        flavorDimensions += "app"
        productFlavors {
            create("prod"){
                dimension = "app"
            }
            create("mock"){
                dimension = "app"
            }
        }

        buildFeatures {
            compose = false // let module which use compose will override compose = true
            aidl = false
            buildConfig = true
            renderScript = false
            shaders = false
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
                excludes += "META-INF/*"
            }
        }
    }
}
