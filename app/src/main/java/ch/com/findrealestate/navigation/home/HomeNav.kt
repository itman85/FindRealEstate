package ch.com.findrealestate.navigation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ch.com.findrealestate.features.home.HomeStateViewModel
import ch.com.findrealestate.navigation.Destinations
import ch.com.findrealestate.ui.screens.HomeScreen

fun NavGraphBuilder.home(navController: NavController, homeViewModel: HomeStateViewModel) {
    composable(route = Destinations.HomeScreen.route) {
        val currentScreenRoute =
            navController.currentDestination?.route
        if (currentScreenRoute == it.destination.route) {
            HomeScreen(viewModel = homeViewModel, navigator = HomeNavigatorImpl(navController))
        }
    }
}
