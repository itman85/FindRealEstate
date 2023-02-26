package ch.com.findrealestate.navigation.detail

import androidx.navigation.NavController
import ch.com.findrealestate.features.detail.DetailNavigator

class DetailNavigatorImpl(private val navController: NavController) : DetailNavigator {
    override fun navigateBack() {
        navController.navigateUp()
    }
}
