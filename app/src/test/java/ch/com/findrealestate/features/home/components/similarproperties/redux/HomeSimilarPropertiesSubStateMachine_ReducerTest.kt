package ch.com.findrealestate.features.home.components.similarproperties.redux

import android.util.Log
import ch.com.findrealestate.features.home.HomeItem
import ch.com.findrealestate.features.home.redux.HomeState
import ch.com.findrealestate.features.home.redux.createSampleProperty
import io.kotlintest.matchers.numerics.shouldBeExactly
import io.kotlintest.matchers.types.shouldBeSameInstanceAs
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Test

internal class HomeSimilarPropertiesSubStateMachine_ReducerTest {

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
    fun `similar properties loaded - update new state`() {
        val similarProperties = listOf(
            createSampleProperty(id = "0123", isFavorite = false),
            createSampleProperty(id = "012345", isFavorite = false),
            createSampleProperty(id = "0123678", isFavorite = false)
        )
        val items = listOf(
            createSampleProperty(id = "123", isFavorite = false),
            createSampleProperty(id = "12345", isFavorite = true),
            createSampleProperty(id = "123678", isFavorite = false),
            createSampleProperty(id = "123679", isFavorite = false),
            createSampleProperty(id = "1236780", isFavorite = false),
            createSampleProperty(id = "1236781", isFavorite = false)
        ).map { HomeItem.PropertyItem(it) }

        val state = subStateMachine.reducer(
            HomeState.PropertiesLoaded(HomeState.Init, items),
            HomeSimilarPropertiesAction.SimilarPropertiesLoaded(similarProperties)
        )
        state.shouldBeTypeOf<HomeState.PropertiesListUpdated> {
            it.items.size.shouldBeExactly(7)
            it.items[0].shouldBeTypeOf<HomeItem.PropertyItem> { item ->
                item.property.id.shouldBe("123")
            }
            it.items[1].shouldBeTypeOf<HomeItem.PropertyItem> { item ->
                item.property.id.shouldBe("12345")
                item.property.isFavorite.shouldBe(true)
            }
            it.items[2].shouldBeTypeOf<HomeItem.PropertyItem> { item ->
                item.property.id.shouldBe("123678")
            }
            it.items[4].shouldBeTypeOf<HomeItem.SimilarPropertiesItem> { item ->
                item.properties.shouldBeSameInstanceAs(similarProperties)
            }
            it.items[6].shouldBeTypeOf<HomeItem.PropertyItem> { item ->
                item.property.id.shouldBe("1236781")
            }
        }
    }
}
