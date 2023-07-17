import ch.com.findrealestate.configureAndroidApplication
import ch.com.findrealestate.configureAndroidCommon
import ch.com.findrealestate.configureBuildVariant
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
       with(target){
           with(pluginManager){
               apply("com.android.application")
           }
           extensions.configure<ApplicationExtension>{
               configureAndroidApplication(this)
               configureAndroidCommon(this)
           }
           extensions.findByType(AndroidComponentsExtension::class.java)?.apply {
               configureBuildVariant(this)
           }
       }
    }
}
