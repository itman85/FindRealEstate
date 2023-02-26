package ch.com.findrealestate.navigation.home

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ch.com.findrealestate.features.home.HomeNavigator
import ch.com.findrealestate.navigation.Destinations
import ch.com.findrealestate.navigation.navigateTo
import javax.inject.Inject

class HomeNavigatorImpl @Inject constructor(private val navController: NavController) :
    HomeNavigator {
    override fun navigateToDetail(propertyId: String) {
        navController.navigateTo(
            route = Destinations.DetailScreen().route,
            args = bundleOf(Destinations.DetailScreen().detailData to propertyId)
        )
    }
}
