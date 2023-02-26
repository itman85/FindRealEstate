package ch.com.findrealestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ch.com.findrealestate.features.home.HomeStateViewModel
import ch.com.findrealestate.navigation.Destinations
import ch.com.findrealestate.navigation.detail.detail
import ch.com.findrealestate.navigation.home.home
import ch.com.findrealestate.ui.theme.FindRealEstateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindRealEstateTheme {
                val navController = rememberNavController()
                val homeViewModel: HomeStateViewModel = viewModel()
                NavHost(
                    navController = navController,
                    startDestination = Destinations.HomeScreen.route
                ) {
                    home(navController, homeViewModel)
                    detail(navController)
                }
            }
        }
    }
}
