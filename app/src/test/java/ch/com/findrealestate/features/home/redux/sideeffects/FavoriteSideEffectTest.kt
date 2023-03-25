package ch.com.findrealestate.features.home.redux.sideeffects

import app.cash.turbine.test
import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.domain.usecase.FavoriteUseCase
import ch.com.findrealestate.features.home.redux.HomeAction
import ch.com.findrealestate.features.home.redux.HomeState
import ch.com.findrealestate.features.home.redux.createSampleProperty
import ch.com.findrealestate.features.home.redux.getState
import com.freeletics.flowredux.GetState
import io.kotlintest.matchers.numerics.shouldBeExactly
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteSideEffectTest {
    private lateinit var sideEffect: FavoriteSideEffect

    @RelaxedMockK
    lateinit var favoriteUseCase: FavoriteUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sideEffect = FavoriteSideEffect(favoriteUseCase)
    }

    @Test
    fun `user click add favorite`() = runTest {
        coEvery { favoriteUseCase.checkFavorite(any()) } returns false
        coEvery { favoriteUseCase.toggleFavorite(any(), any()) } returns Unit
        val currentState = HomeState.PropertiesLoaded(
            HomeState.Init,
            properties = listOf(
                createSampleProperty(id = "123", isFavorite = false),
                createSampleProperty(id = "12345", isFavorite = false),
                createSampleProperty(id = "123678", isFavorite = false)
            )
        )
        sideEffect.invoke(
            flow { emit(HomeAction.FavoriteClick(propertyId = "123")) },
            getState(currentState)
        ).test {
            // get error "Backend Internal error: Exception during IR lowering" have to clean project and re-run
            awaitItem().shouldBe(HomeAction.FavoriteUpdated("123", true))
            awaitComplete()
        }
    }

    @Test
    fun `user click remove favorite`() = runTest {
        coEvery { favoriteUseCase.checkFavorite(any()) } returns true
        val currentState = HomeState.PropertiesLoaded(
            HomeState.Init,
            properties = listOf(
                createSampleProperty(id = "123", isFavorite = true),
                createSampleProperty(id = "12345", isFavorite = false),
                createSampleProperty(id = "123678", isFavorite = false)
            )
        )
        sideEffect.invoke(
            flow { emit(HomeAction.FavoriteClick(propertyId = "12345")) },
            getState(currentState)
        ).test {
            awaitItem().shouldBe(HomeAction.ConfirmRemoveFavorite("12345"))
            awaitComplete()
        }
    }

    @Test
    fun `user confirm remove favorite`() = runTest {
        val currentState = HomeState.PropertiesLoaded(
            HomeState.Init,
            properties = listOf(
                createSampleProperty(id = "123", isFavorite = true),
                createSampleProperty(id = "12345", isFavorite = false),
                createSampleProperty(id = "123678", isFavorite = false)
            )
        )
        sideEffect.invoke(
            flow { emit(HomeAction.ConfirmRemoveFavoriteYesClick(propertyId = "12345")) },
            getState(currentState)
        ).test {
            awaitItem().shouldBe(HomeAction.FavoriteUpdated("12345", false))
            awaitComplete()
        }
    }
}
