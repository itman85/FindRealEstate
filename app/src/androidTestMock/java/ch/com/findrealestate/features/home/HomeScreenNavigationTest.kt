package ch.com.findrealestate.features.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.testing.TestNavHostController
import ch.com.findrealestate.TestActivity
import ch.com.findrealestate.mock.PropertiesApiMocker
import ch.com.findrealestate.navigation.Destinations
import ch.com.findrealestate.navigation.detail.detail
import ch.com.findrealestate.navigation.home.home
import ch.com.findrealestate.ui.theme.FindRealEstateTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotlintest.shouldBe
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
class HomeScreenNavigationTest {

    private val hiltRule by lazy { HiltAndroidRule(this) }

    private val composeTestRule = createAndroidComposeRule<TestActivity>()

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule).around(composeTestRule)

    @Inject
    lateinit var propertiesApiMocker: PropertiesApiMocker

    @Inject
    lateinit var mockWebServer: MockWebServer

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()
        mockWebServer.start()
    }

    @After
    fun teardown() {
        if (this::mockWebServer.isInitialized)
            mockWebServer.shutdown()
    }

    @Test
    fun loadHomeScreen_OpenDetail() {
        // this is properties api
        propertiesApiMocker.getPropertiesSuccess()
        // this is for similar properties api
        propertiesApiMocker.getSimilarPropertiesSuccess()

        // this mock is for detail property api
        val propertyId = "104123262"
        propertiesApiMocker.getDetailPropertySuccess(propertyId)

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
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
