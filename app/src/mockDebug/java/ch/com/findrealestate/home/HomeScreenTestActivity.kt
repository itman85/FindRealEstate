package ch.com.findrealestate.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import ch.com.findrealestate.features.home.HomeScreen
import ch.com.findrealestate.navigation.home.HomeNavigatorImpl
import ch.com.findrealestate.ui.theme.FindRealEstateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindRealEstateTheme {
                val navController = rememberNavController()
                HomeScreen(navigator = HomeNavigatorImpl(navController))
            }
        }
    }
}
