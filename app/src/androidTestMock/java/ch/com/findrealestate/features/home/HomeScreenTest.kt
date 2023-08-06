package ch.com.findrealestate.features.home

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import ch.com.findrealestate.R
import ch.com.findrealestate.TestActivity
import ch.com.findrealestate.mock.PropertiesApiMocker
import ch.com.findrealestate.navigation.home.HomeNavigatorImpl
import ch.com.findrealestate.ui.theme.FindRealEstateTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
class HomeScreenTest {

    private val hiltRule by lazy { HiltAndroidRule(this) }

    private val composeTestRule = createAndroidComposeRule<TestActivity>()

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule).around(composeTestRule)

    @Inject
    lateinit var propertiesApiMocker: PropertiesApiMocker

    @Inject
    lateinit var mockWebServer: MockWebServer

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

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
    fun loadHomeScreen_MockProperties_Success() {
        // this is properties api
        propertiesApiMocker.getPropertiesSuccess()
        // this is for similar properties api
        propertiesApiMocker.getSimilarPropertiesSuccess()

        composeTestRule.setContent {
            HomeScreenContent()
        }

        composeTestRule.waitUntil(timeoutMillis = 10000L) {
            composeTestRule.onAllNodesWithTag("the_list").fetchSemanticsNodes().isNotEmpty()

        }
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("Home Before")
        composeTestRule.onNode(hasText(context.resources.getString(R.string.loading_text)))
            .assertIsDisplayed()

        composeTestRule.waitUntil(timeoutMillis = 10000L) {
            composeTestRule.onAllNodesWithTag("property_item").fetchSemanticsNodes().isNotEmpty()

        }
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("Home After")
        composeTestRule.onNode(hasText("Street 999, NE, CH")).assertIsDisplayed()
    }

    @Test
    fun loadHomeScreen_MockProperties_Failed() {
        // this is properties api
        propertiesApiMocker.getPropertiesFailed()

        composeTestRule.setContent {
            HomeScreenContent()
        }

        composeTestRule.waitUntil(timeoutMillis = 10000L) {
            composeTestRule.onAllNodesWithTag("the_list").fetchSemanticsNodes().isNotEmpty()

        }
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("Home After")
        composeTestRule.onNode(hasText("Error happen")).assertIsDisplayed()
    }
}

@Composable
fun HomeScreenContent(navigator: HomeNavigator? = null) {
    FindRealEstateTheme {
        val navController = rememberNavController()
        HomeScreen(navigator = navigator ?: HomeNavigatorImpl(navController))
    }
}
