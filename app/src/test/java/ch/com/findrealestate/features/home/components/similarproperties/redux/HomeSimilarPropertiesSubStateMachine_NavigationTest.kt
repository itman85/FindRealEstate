package ch.com.findrealestate.features.home.components.similarproperties.redux

import android.util.Log
import app.cash.turbine.test
import ch.com.findrealestate.features.home.HomeItem
import ch.com.findrealestate.features.home.redux.*
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeSimilarPropertiesSubStateMachine_NavigationTest {

    lateinit var subStateMachine: HomeSimilarPropertiesSubStateMachine

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        subStateMachine = HomeSimilarPropertiesSubStateMachine(mockk())
        // this solve error:  android.util.log not mocked
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @Test
    fun `similar property click open detail`() = runTest {
        val currentState = HomeState.PropertiesLoaded(
            HomeState.Init,
            items = listOf(
                createSampleProperty(id = "123", isFavorite = true),
                createSampleProperty(id = "12345", isFavorite = false),
                createSampleProperty(id = "123678", isFavorite = false)
            ).map { HomeItem.PropertyItem(it) }
        )

        // should call navigation test to collect navigation flow first,
        // use this way navigationFlow.receiveAsFlow() to test navigation for this sub state machine only
        launch {
            subStateMachine.navigationFlow.receiveAsFlow().test {
                awaitItem().shouldBeTypeOf<HomeSimilarPropertiesNavigation.OpenSimilarPropertyDetail> {
                    it.propertyId.shouldBe("123")
                }
                cancelAndIgnoreRemainingEvents()
            }
        }

        launch {
            subStateMachine.navigationSideEffect(
                flow { emit(HomeSimilarPropertiesAction.SimilarPropertyClick("123")) },
                getState(currentState)
            ).test {
                awaitComplete()
            }
        }
    }
}
