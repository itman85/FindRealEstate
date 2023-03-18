package ch.com.findrealestate.navigation.home

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import ch.com.findrealestate.features.home.HomeNavigator
import ch.com.findrealestate.navigation.Destinations
import ch.com.findrealestate.navigation.navigateTo

class HomeNavigatorImpl(private val navController: NavController) :
    HomeNavigator {
    override fun navigateToDetail(propertyId: String) {
        navController.navigateTo(
            route = Destinations.DetailScreen().route,
            args = bundleOf(Destinations.DetailScreen().detailData to propertyId)
        )
    }
}
