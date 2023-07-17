import ch.com.findrealestate.configureAndroidCommon
import ch.com.findrealestate.configureAndroidLibrary
import ch.com.findrealestate.configureBuildVariant
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension> {
                configureAndroidLibrary(this)
                configureAndroidCommon(this)
            }
            extensions.findByType(AndroidComponentsExtension::class.java)?.apply {
               configureBuildVariant(this)
            }
        }
    }
}
