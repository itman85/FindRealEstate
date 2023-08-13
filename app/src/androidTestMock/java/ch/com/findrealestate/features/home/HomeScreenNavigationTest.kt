package ch.com.findrealestate.features.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.testing.TestNavHostController
import ch.com.findrealestate.base.MockApiActivityTest
import ch.com.findrealestate.mock.PropertiesApiMocker
import ch.com.findrealestate.navigation.Destinations
import ch.com.findrealestate.navigation.detail.detail
import ch.com.findrealestate.navigation.home.home
import ch.com.findrealestate.ui.theme.FindRealEstateTheme
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotlintest.shouldBe
import org.junit.Test

@HiltAndroidTest
class HomeScreenNavigationTest : MockApiActivityTest<PropertiesApiMocker>() {

    @Test
    fun loadHomeScreen_OpenDetail() {
        // this is properties api
        apiMocker.getPropertiesSuccess()
        // this is for similar properties api
        apiMocker.getSimilarPropertiesSuccess()

        // this mock is for detail property api
        val propertyId = "104123262"
        apiMocker.getDetailPropertySuccess(propertyId)

        composeTestRule.setContent {
            ScreensNavigationContent(navController)
        }

        composeTestRule.waitUntil(timeoutMillis = 10000L) {
            composeTestRule.onAllNodesWithTag("property_item").fetchSemanticsNodes().isNotEmpty()

        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("Home")
        composeTestRule.onAllNodesWithTag("property_item").onFirst().performClick()

        composeTestRule.waitForIdle()
        // with TestNavHostController, it's able to assert route
        val route = navController.currentDestination?.route
        route.shouldBe(Destinations.DetailScreen().route)


        composeTestRule.onRoot(useUnmergedTree = true).printToLog("Detail Loading")
        composeTestRule.onNode(hasText("Loading detail data for Property Id $propertyId"))
            .assertIsDisplayed()
        // waiting for detail loaded
        composeTestRule.waitUntil(timeoutMillis = 10000L) {
            composeTestRule.onAllNodesWithTag("detail_data").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("Detail Loaded")
        composeTestRule.onNode(hasText("Open property website")).assertIsDisplayed()
    }
}

@Composable
fun ScreensNavigationContent(navController: NavHostController) {
    FindRealEstateTheme {
        NavHost(
            navController = navController,
            startDestination = Destinations.HomeScreen.route
        ) {
            home(navController)
            detail(navController)
        }
    }
}
