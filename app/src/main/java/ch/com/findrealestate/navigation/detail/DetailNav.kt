package ch.com.findrealestate.navigation.detail

import android.app.Activity
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ch.com.findrealestate.features.detail.DetailScreen
import ch.com.findrealestate.navigation.Destinations

fun NavGraphBuilder.detail(navController: NavController) {
    composable(route = Destinations.DetailScreen().route) { entry ->
        val data = entry.arguments?.getString(Destinations.DetailScreen().detailData)
        DetailScreen(
            propertyId = data,
            navigator = DetailNavigatorImpl(navController, LocalContext.current as Activity)
        )
    }
}
