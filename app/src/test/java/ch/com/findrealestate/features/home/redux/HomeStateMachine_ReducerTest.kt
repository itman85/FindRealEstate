package ch.com.findrealestate.features.home.redux

import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.domain.usecase.GetPropertiesUseCase
import ch.com.findrealestate.features.home.HomeItem
import io.kotlintest.matchers.numerics.shouldBeExactly
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class HomeStateMachine_ReducerTest {

    @RelaxedMockK
    lateinit var getPropertiesUseCase: GetPropertiesUseCase

    lateinit var stateMachine: HomeStateMachine

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        stateMachine = HomeStateMachine(getPropertiesUseCase, mockk(), mockk())
    }

    @Test
    fun `start loading`() {
        val state = stateMachine.reducer(HomeState.Init, HomeAction.StartLoadData)
        state.shouldBeTypeOf<HomeState.Loading>()
    }

    @Test
    fun `data loaded`() {
        val properties = listOf(
            createSampleProperty(id = "123", isFavorite = false),
            createSampleProperty(id = "12345", isFavorite = false),
            createSampleProperty(id = "123678", isFavorite = false)
        )
        val state = stateMachine.reducer(
            HomeState.Loading(HomeState.Init),
            HomeAction.DataLoaded(properties)
        )
        state.shouldBeTypeOf<HomeState.PropertiesLoaded> {
            it.items.size.shouldBeExactly(3)
            it.items[0].shouldBeTypeOf<HomeItem.PropertyItem> { item ->
                item.property.id.shouldBe("123")
            }
            it.items[1].shouldBeTypeOf<HomeItem.PropertyItem> { item ->
                item.property.id.shouldBe("12345")
            }
            it.items[2].shouldBeTypeOf<HomeItem.PropertyItem> { item ->
                item.property.id.shouldBe("123678")
            }
        }
    }

    @Test
    fun `favorite added`() {
        val currentState = HomeState.PropertiesLoaded(
            HomeState.Init,
            items = listOf(
                createSampleProperty(id = "123", isFavorite = false),
                createSampleProperty(id = "12345", isFavorite = false),
                createSampleProperty(id = "123678", isFavorite = false)
            ).map { HomeItem.PropertyItem(it) }
        )
        val state = stateMachine.reducer(currentState, HomeAction.FavoriteUpdated("123", true))
        state.shouldBeTypeOf<HomeState.AddFavoriteSuccessful> {
            it.items.size.shouldBeExactly(3)
            it.items[0].shouldBeTypeOf<HomeItem.PropertyItem> { item ->
                item.property.id.shouldBe("123")
                item.property.isFavorite.shouldBe(true)
            }
            it.favoriteProperty.id.shouldBe("123")
        }
    }

    @Test
    fun `favorite remove no click`() {
        val currentState = HomeState.PropertiesLoaded(
            HomeState.Init,
            items = listOf(
                createSampleProperty(id = "123", isFavorite = true),
                createSampleProperty(id = "12345", isFavorite = false),
                createSampleProperty(id = "123678", isFavorite = false)
            ).map { HomeItem.PropertyItem(it) }
        )
        val state = stateMachine.reducer(currentState, HomeAction.ConfirmRemoveFavoriteNoClick)
        state.shouldBeTypeOf<HomeState.PropertiesListUpdated> {
            it.items.size.shouldBeExactly(3)
            it.items[0].shouldBeTypeOf<HomeItem.PropertyItem> { item ->
                item.property.id.shouldBe("123")
                item.property.isFavorite.shouldBe(true)
            }
            it.items[1].shouldBeTypeOf<HomeItem.PropertyItem> { item ->
                item.property.id.shouldBe("12345")
                item.property.isFavorite.shouldBe(false)
            }
            it.items[2].shouldBeTypeOf<HomeItem.PropertyItem> { item ->
                item.property.id.shouldBe("123678")
                item.property.isFavorite.shouldBe(false)
            }
        }
    }
}


fun createSampleProperty(
    id: String,
    imageUrl: String = "",
    title: String = "",
    price: Long = 0,
    address: String = "",
    isFavorite: Boolean = false
) = Property(id, imageUrl, title, price, address, isFavorite)
