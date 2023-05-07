package ch.com.findrealestate.features.home.redux

import android.util.Log
import app.cash.turbine.test
import ch.com.findrealestate.domain.usecase.GetPropertiesUseCase
import ch.com.findrealestate.features.home.HomeItem
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class HomeStateMachine_NavigationTest {
    @RelaxedMockK
    lateinit var getPropertiesUseCase: GetPropertiesUseCase

    lateinit var stateMachine: HomeStateMachine

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        stateMachine = HomeStateMachine(getPropertiesUseCase, mockk(), mockk())
        // this solve error:  android.util.log not mocked
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @Test
    fun `property click open detail`() = runTest {
        val currentState = HomeState.PropertiesLoaded(
            HomeState.Init,
            items = listOf(
                createSampleProperty(id = "123", isFavorite = true),
                createSampleProperty(id = "12345", isFavorite = false),
                createSampleProperty(id = "123678", isFavorite = false)
            ).map { HomeItem.PropertyItem(it) }
        )

        // should call navigation test to collect navigation flow first,
        // use navigationFlow.receiveAsFlow() instead of navigation(), this way test navigation for this stateMachine only, not for sub state machine
        launch {
            stateMachine.navigationFlow.receiveAsFlow().test {
                awaitItem().shouldBeTypeOf<HomeNavigation.OpenDetailScreen> {
                    it.propertyId.shouldBe("123")
                }
                cancelAndIgnoreRemainingEvents()
            }
        }

        launch {
            stateMachine.navigationSideEffect(
                flow { emit(HomeAction.PropertyClick("123")) },
                getState(currentState)
            ).test {
                awaitComplete()
            }
        }
    }
}
