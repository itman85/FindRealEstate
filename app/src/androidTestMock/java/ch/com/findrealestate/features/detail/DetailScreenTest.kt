package ch.com.findrealestate.features.detail

import android.app.Activity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.rememberNavController
import ch.com.findrealestate.base.MockApiActivityTest
import ch.com.findrealestate.mock.PropertiesApiMocker
import ch.com.findrealestate.navigation.detail.DetailNavigatorImpl
import ch.com.findrealestate.ui.theme.FindRealEstateTheme
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test


@HiltAndroidTest
class DetailScreenTest : MockApiActivityTest<PropertiesApiMocker>() {

    @Test
    fun firstTest() {
        composeTestRule.setContent {
            Text(text = "Test")
        }
        composeTestRule.onNode(hasText("Test")).assertExists()
    }

    @Test
    fun detailDisplayOpenWebsiteButton_MockDetailListing() {
        val propertyId = "104123262"
        // this will mock detail listing api use mockWebServer
        apiMocker.getDetailPropertySuccess(propertyId)

        composeTestRule.setContent {
            DetailScreenContent(propertyId)
        }
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("Detail Before")
        composeTestRule.waitUntil(timeoutMillis = 5000L) {
            composeTestRule.onAllNodesWithTag("detail_data").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("Detail After")
        composeTestRule.onNode(hasText("Open property website")).assertIsDisplayed()

    }
}

@Composable
fun DetailScreenContent(propertyId: String, navigator: DetailNavigator? = null) {
    FindRealEstateTheme {
        val navController = rememberNavController()
        DetailScreen(
            propertyId = propertyId, navigator = navigator ?: DetailNavigatorImpl(
                navController,
                LocalContext.current as Activity
            )
        )
    }
}
