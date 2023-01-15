package ch.com.findrealestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.com.findrealestate.redux.home.HomeStateViewModel
import ch.com.findrealestate.ui.screens.HomeScreen
import ch.com.findrealestate.ui.theme.FindRealEstateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindRealEstateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FindRealEstateAppComponent()
                }
            }
        }
    }

    @Composable
    private fun FindRealEstateAppComponent() {
        val homeViewModel: HomeStateViewModel = viewModel()
        HomeScreen(homeViewModel)
    }
}
