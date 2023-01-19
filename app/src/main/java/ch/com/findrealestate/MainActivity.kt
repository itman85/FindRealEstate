package ch.com.findrealestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.com.findrealestate.features.detail.DetailScreen
import ch.com.findrealestate.features.home.HomeStateViewModel
import ch.com.findrealestate.navigation.NavigationRoutes
import ch.com.findrealestate.navigation.NavigationManager
import ch.com.findrealestate.ui.screens.HomeScreen
import ch.com.findrealestate.ui.theme.FindRealEstateTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindRealEstateTheme {
                val navController = rememberNavController()
                navigationManager.commands.collectAsState().value.also { command ->
                    if (command.destination.isNotEmpty()) {
                        navController.navigate(command.destination)
                    }
                }
                val homeViewModel: HomeStateViewModel = viewModel()
                NavHost(navController = navController, startDestination = NavigationRoutes.home.destination){
                    composable(route = NavigationRoutes.home.destination){
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            HomeScreen(homeViewModel)
                        }
                    }
                    composable(route = NavigationRoutes.detail.destination){
                        DetailScreen()
                    }
                }
            }
        }
    }
}
