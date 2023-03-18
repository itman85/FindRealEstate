package ch.com.findrealestate.navigation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ch.com.findrealestate.navigation.Destinations
import ch.com.findrealestate.features.home.HomeScreen

fun NavGraphBuilder.home(navController: NavController) {
    composable(route = Destinations.HomeScreen.route) {
        HomeScreen(navigator = HomeNavigatorImpl(navController))
    }
}
