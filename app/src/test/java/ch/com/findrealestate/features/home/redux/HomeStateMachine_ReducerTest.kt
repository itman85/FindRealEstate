package ch.com.findrealestate.features.home.redux

import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.domain.usecase.GetPropertiesUseCase
import com.freeletics.flowredux.GetState
import io.kotlintest.matchers.numerics.shouldBeExactly
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class HomeStateMachine_ReducerTest{

    @RelaxedMockK
    lateinit var getPropertiesUseCase: GetPropertiesUseCase

    lateinit var stateMachine: HomeStateMachine

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        stateMachine = HomeStateMachine(getPropertiesUseCase, mockk())
    }

    @Test
    fun `start loading`(){
        val state = stateMachine.reducer(HomeState.Init,HomeAction.StartLoadData)
        state.shouldBeTypeOf<HomeState.Loading>()
    }

    @Test
    fun `data loaded`(){
        val properties = listOf(
            createSampleProperty(id = "123", isFavorite = false),
            createSampleProperty(id = "12345", isFavorite = false),
            createSampleProperty(id = "123678", isFavorite = false)
        )
        val state = stateMachine.reducer(HomeState.Loading(HomeState.Init),HomeAction.DataLoaded(properties))
        state.shouldBeTypeOf<HomeState.PropertiesLoaded>{
            it.properties.size.shouldBeExactly(3)
            it.properties[0].id.shouldBe("123")
            it.properties[1].id.shouldBe("12345")
            it.properties[2].id.shouldBe("123678")
        }
    }

    @Test
    fun `favorite added`(){
        val currentState = HomeState.PropertiesLoaded(
            HomeState.Init,
            properties = listOf(
                createSampleProperty(id = "123", isFavorite = true),
                createSampleProperty(id = "12345", isFavorite = false),
                createSampleProperty(id = "123678", isFavorite = false)
            )
        )
        val state = stateMachine.reducer(currentState,HomeAction.FavoriteUpdated("123",true))
        state.shouldBeTypeOf<HomeState.AddFavoriteSuccessful>{
            it.properties.size.shouldBeExactly(3)
            it.properties[0].id.shouldBe("123")
            it.properties[0].isFavorite.shouldBe(true)
            it.favoriteProperty.id.shouldBe("123")
        }
    }
}

fun <S> getState(state: S): GetState<S> {
    return object : GetState<S> {
        override fun invoke(): S = state
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
