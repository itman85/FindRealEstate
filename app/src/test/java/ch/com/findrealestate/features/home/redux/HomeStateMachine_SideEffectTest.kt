package ch.com.findrealestate.features.home.redux

import android.util.Log
import app.cash.turbine.test
import ch.com.findrealestate.domain.usecase.GetPropertiesUseCase
import io.kotlintest.matchers.numerics.shouldBeExactly
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class HomeStateMachine_SideEffectTest {

    @RelaxedMockK
    lateinit var getPropertiesUseCase: GetPropertiesUseCase

    lateinit var stateMachine: HomeStateMachine

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        stateMachine = HomeStateMachine(getPropertiesUseCase, mockk())
        // this solve error:  android.util.log not mocked
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @Test
    fun `load properties successfully`() = runTest {
        coEvery { getPropertiesUseCase.invoke() } returns listOf(
            createSampleProperty(id = "123", isFavorite = false),
            createSampleProperty(id = "12345", isFavorite = false),
            createSampleProperty(id = "123678", isFavorite = false)
        )
        stateMachine.loadPropertiesSideEffect(
            flow { emit(HomeAction.StartLoadData) },
            getState(HomeState.Init)
        ).test {
            awaitItem().shouldBeTypeOf<HomeAction.DataLoaded>{
                it.properties.size.shouldBeExactly(3)
                it.properties[0].id.shouldBe("123")
                it.properties[1].id.shouldBe("12345")
                it.properties[2].id.shouldBe("123678")
            }
            awaitComplete()
        }
    }

    @Test
    fun `load properties error network timeout`() = runTest {
        coEvery { getPropertiesUseCase.invoke() }.throws(Exception("Network timeout"))
        stateMachine.loadPropertiesSideEffect(
            flow { emit(HomeAction.StartLoadData) },
            getState(HomeState.Init)
        ).test {
            awaitItem().shouldBeTypeOf<HomeAction.DataLoadedError>{
                it.error.shouldBe("Network timeout")
            }
            awaitComplete()
        }
    }
}
