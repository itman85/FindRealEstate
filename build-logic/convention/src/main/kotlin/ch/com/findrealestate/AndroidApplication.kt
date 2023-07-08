package ch.com.findrealestate

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

internal fun Project.configureAndroidApplication(extension: ApplicationExtension){
    extension.apply {
        defaultConfig {
            targetSdk = 33
        }
        signingConfigs {
            create("release"){
                storeFile = file("../findrealestate.keystore")
                storePassword = "itman85" // just for simplicity, but it totally can hide these info
                keyAlias = "itman85"
                keyPassword = "itman85"
            }
        }
        buildTypes {
            debug {
                applicationIdSuffix = ".debug"
            }
            release {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
}
