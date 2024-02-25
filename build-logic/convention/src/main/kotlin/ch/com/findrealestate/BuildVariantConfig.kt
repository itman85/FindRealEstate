package ch.com.findrealestate

import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Project

internal fun Project.configureBuildVariant(extension:AndroidComponentsExtension<*,*,*>){
    extension.apply {
        beforeVariants {variantBuilder ->
            if(variantBuilder.buildType == "release" && variantBuilder.flavorName == "mock") {
                // ignore release builds for mock flavor in gradle
                variantBuilder.enable = false
            }
        }
    }
}
