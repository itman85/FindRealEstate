package ch.com.findrealestate.features.home.components.similarproperties.redux

import android.util.Log
import app.cash.turbine.test
import ch.com.findrealestate.domain.usecase.GetSimilarProperties
import ch.com.findrealestate.features.home.redux.HomeAction
import ch.com.findrealestate.features.home.redux.HomeState
import ch.com.findrealestate.features.home.redux.createSampleProperty
import ch.com.findrealestate.features.home.redux.getState
import io.kotlintest.matchers.numerics.shouldBeExactly
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeSimilarPropertiesSubStateMachine_SideEffectTest {
    @RelaxedMockK
    lateinit var getSimilarProperties: GetSimilarProperties

    lateinit var subStateMachine: HomeSimilarPropertiesSubStateMachine

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        subStateMachine = HomeSimilarPropertiesSubStateMachine(getSimilarProperties)
        // this solve error:  android.util.log not mocked
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @Test
    fun loadPropertiesSideEffectTest_Success() = runTest {
        coEvery { getSimilarProperties.invoke() } returns listOf(
            createSampleProperty(id = "123", isFavorite = false),
            createSampleProperty(id = "12345", isFavorite = false),
            createSampleProperty(id = "123678", isFavorite = false),
            createSampleProperty(id = "123679", isFavorite = false),
            createSampleProperty(id = "1236780", isFavorite = false),
            createSampleProperty(id = "1236781", isFavorite = false)

        )
        subStateMachine.loadSimilarPropertiesSideEffect(
            flow { emit(HomeAction.DataLoaded(emptyList())) },
            getState(HomeState.Init)
        ).test {
            awaitItem().shouldBeTypeOf<HomeSimilarPropertiesAction.SimilarPropertiesLoaded> {
                it.properties.size.shouldBeExactly(6)
                it.properties[0].id.shouldBe("123")
                it.properties[1].id.shouldBe("12345")
                it.properties[2].id.shouldBe("123678")
                it.properties[5].id.shouldBe("1236781")
            }
            awaitComplete()
        }
    }

    @Test
    fun loadPropertiesSideEffectTest_Error() = runTest {
        coEvery { getSimilarProperties.invoke() } throws Exception("Network timeout")
        subStateMachine.loadSimilarPropertiesSideEffect(
            flow { emit(HomeAction.DataLoaded(emptyList())) },
            getState(HomeState.Init)
        ).test {
            awaitItem().shouldBeTypeOf<HomeSimilarPropertiesAction.SimilarPropertiesLoadedError> {
                it.error.shouldBe("Network timeout")
            }
            awaitComplete()
        }
    }
}
