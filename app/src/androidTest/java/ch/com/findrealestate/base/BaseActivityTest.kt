package ch.com.findrealestate.base

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import ch.com.findrealestate.TestActivity
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain

abstract class BaseActivityTest {
    private val hiltRule by lazy { HiltAndroidRule(this) }

    protected val composeTestRule = createAndroidComposeRule<TestActivity>()

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule).around(composeTestRule)

    protected val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    protected lateinit var navController: TestNavHostController

    abstract fun setUp()
    abstract fun tearDown()

    @Before
    fun init() {
        hiltRule.inject()
        setUp()
        navController = TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
    }

    @After
    fun destroy() {
        tearDown()
    }
}
