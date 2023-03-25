package ch.com.findrealestate.navigation.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.NavController
import ch.com.findrealestate.features.detail.DetailNavigator

class DetailNavigatorImpl(
    private val navController: NavController,
    private val activity: Activity
) : DetailNavigator {
    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun navigateOpenChromeTab(url: String) {
        openChromeTab(activity, url)
    }

    private fun openChromeTab(activity: Activity, url: String) {

        val package_name = "com.android.chrome"

        val builder = CustomTabsIntent.Builder()


        builder.setShowTitle(true)


        builder.setInstantAppsEnabled(true)


        val customBuilder = builder.build()

        if (package_name != null) {

            customBuilder.intent.setPackage(package_name)

            customBuilder.launchUrl(activity, Uri.parse(url))
        } else {

            val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            activity.startActivity(i)
        }

    }
}
