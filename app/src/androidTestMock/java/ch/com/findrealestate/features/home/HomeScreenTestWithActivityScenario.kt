package ch.com.findrealestate.features.home

import android.content.Intent
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import ch.com.findrealestate.home.HomeScreenTestActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@HiltAndroidTest
class HomeScreenTestWithActivityScenario {

    private lateinit var activityScenario: ActivityScenario<HomeScreenTestActivity>

    // this way will launch activity with action main and category launch
    // private val composeTestRule: ComposeContentTestRule = createAndroidComposeRule<HomeScreenTestActivity>()

    //@get:Rule(order = 0)
    private val hiltRule by lazy { HiltAndroidRule(this) }

    //@get:Rule(order = 1)
    private val composeTestRule by lazy {
        AndroidComposeTestRule(EmptyTestRule()) {
            var activity: HomeScreenTestActivity? = null
            if (this::activityScenario.isInitialized)
                activityScenario.onActivity { activity = it }
            checkNotNull(activity) { "Activity didn't launch" }
        }
    }

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule).around(composeTestRule)

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun firstTest() {
        composeTestRule.setContent {
            Text(text = "Test")
        }
    }

    @Test
    fun testHomeScreen() {

        ActivityScenario.launch<HomeScreenTestActivity>(
            Intent(ApplicationProvider.getApplicationContext(), HomeScreenTestActivity::class.java)
        ).use {
            activityScenario = it
            composeTestRule.onNodeWithText("Real Estate").assertIsDisplayed()
        }

        //  composeTestRule.onNodeWithTag("the_list").onChildAt(0).assert(hasText("999"))
    }

    @Test
    fun test() {
        ActivityScenario.launch<HomeScreenTestActivity>(
            Intent(ApplicationProvider.getApplicationContext(), HomeScreenTestActivity::class.java)
        ).use {
            activityScenario = it
            composeTestRule.onNode(hasText("Hello")).assertExists()
        }
    }
}

class EmptyTestRule : TestRule {
    override fun apply(base: Statement, description: Description) = base
}


