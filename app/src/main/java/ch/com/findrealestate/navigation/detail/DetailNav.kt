package ch.com.findrealestate.navigation.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ch.com.findrealestate.features.detail.DetailScreen
import ch.com.findrealestate.navigation.Destinations
import ch.com.findrealestate.navigation.parcelableData

fun NavGraphBuilder.detail(navController: NavController) {
    composable(route = Destinations.DetailScreen().route) { entry ->
        val currentScreenRoute =
            navController.currentDestination?.route
        if (currentScreenRoute == Destinations.DetailScreen().route) {
            val data = entry.arguments?.getString(Destinations.DetailScreen().detailData)
            DetailScreen( propertyId = data,navigator = DetailNavigatorImpl(navController))
        }
    }
}
