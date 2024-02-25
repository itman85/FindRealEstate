package ch.com.findrealestate

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(
    application = HiltTestApplication::class,
    sdk = [28] // Robolectric doesn't support legacy resources mode after android P
)
class RobolectricTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private var activity: MainActivity? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        hiltRule.inject()
        activity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotBeNull() {
        assertNotNull(activity)
    }
}
