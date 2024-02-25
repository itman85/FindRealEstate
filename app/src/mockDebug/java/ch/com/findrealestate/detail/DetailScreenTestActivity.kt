package ch.com.findrealestate.detail

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import ch.com.findrealestate.features.detail.DetailScreen
import ch.com.findrealestate.features.home.HomeScreen
import ch.com.findrealestate.navigation.detail.DetailNavigatorImpl
import ch.com.findrealestate.navigation.home.HomeNavigatorImpl
import ch.com.findrealestate.ui.theme.FindRealEstateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScreenTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindRealEstateTheme {
                val navController = rememberNavController()
                DetailScreen(
                    propertyId = "",// todo pass id here
                    navigator = DetailNavigatorImpl(navController, LocalContext.current as Activity)
                )
            }
        }
    }
}
