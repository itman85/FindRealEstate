package ch.com.findrealestate.features.home.redux

import android.util.Log
import androidx.annotation.VisibleForTesting
import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.domain.usecase.GetPropertiesUseCase
import ch.com.findrealestate.features.home.HomeItem
import ch.com.findrealestate.features.home.components.similarproperties.redux.HomeSimilarPropertiesSubStateMachine
import ch.com.findrealestate.features.home.redux.sideeffects.FavoriteSideEffect
import com.freeletics.flowredux.*
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@ViewModelScoped
class HomeStateMachine @Inject constructor(
    private val getPropertiesUseCase: GetPropertiesUseCase,
    private val favoriteSideEffect: FavoriteSideEffect,
    homeSimilarPropertiesSubStateMachine: HomeSimilarPropertiesSubStateMachine
) : CompositeStateMachine<HomeState, HomeBaseAction, HomeBaseNavigation>() {

    override val subStateMachines: List<SubStateMachine<HomeState, HomeBaseAction, HomeBaseNavigation>> =
        listOf(homeSimilarPropertiesSubStateMachine)

    override val initialState: HomeState = HomeState.Init

    override val initAction: HomeAction = HomeAction.StartLoadData

    override fun sideEffects(): List<SideEffect<HomeState, HomeBaseAction>> =
        createSubStateMachineSideEffects(
            loadPropertiesSideEffect,
            favoriteSideEffect,
            propertyClickSideEffect,
            navigationSideEffect
        )

    override fun reducer(): Reducer<HomeState, HomeBaseAction> =
        createSubStateMachineReducers(this::reducer)

    fun reducer(state: HomeState, action: HomeBaseAction): HomeState {
        return when (action) {
            is HomeAction.StartLoadData -> HomeState.Loading(state)

            is HomeAction.DataLoadedError -> HomeState.Error(state, action.error ?: "")

            is HomeAction.DataLoaded -> HomeState.PropertiesLoaded(
                state,
                action.properties.createHomeItemsList()
            )

            is HomeAction.FavoriteUpdated -> {
                val items = state.items.filterIsInstance<HomeItem.PropertyItem>().map {
                    if (it.property.id == action.propertyId)
                        it.copy(property = it.property.copy(isFavorite = action.isFavorite))
                    else it
                }
                if (action.isFavorite) {
                    HomeState.AddFavoriteSuccessful(
                        state,
                        items = items,
                        favoriteProperty = items.first { it.property.id == action.propertyId }.property
                    )
                } else {
                    HomeState.PropertiesListUpdated(state, items = items)
                }
            }

            is HomeAction.FavoriteDialogYesClick,
            is HomeAction.ConfirmRemoveFavoriteNoClick -> HomeState.PropertiesListUpdated(
                state,
                items = state.items
            )

            is HomeAction.ConfirmRemoveFavorite -> HomeState.ConfirmFavoriteRemoved(
                state,
                action.propertyId
            )
            else -> state
        }
    }

    private fun List<Property>.createHomeItemsList(): List<HomeItem> =
        this.map { property ->
            HomeItem.PropertyItem(property = property)
        }


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val loadPropertiesSideEffect: SideEffect<HomeState, HomeBaseAction> = { actions, _ ->
        actions.ofType(HomeAction.StartLoadData::class)
            .onEach { Log.d("HomeSM", "Start Loading Properties") }
            .flatMapLatest {
                flow {
                    try {
                        // load data
                        val properties = getProperties()
                        emit(HomeAction.DataLoaded(properties))
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        emit(HomeAction.DataLoadedError(ex.message))
                    }
                }
            }
    }


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val navigationSideEffect = createNavigationSideEffect<HomeBaseAction> { _, action ->
        when (action) {
            is HomeAction.PropertyClick -> HomeNavigation.OpenDetailScreen(action.propertyId)
            else -> null
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val propertyClickSideEffect: SideEffect<HomeState, HomeBaseAction> = { actions, _ ->
        actions.ofType(HomeAction.PropertyClick::class)
            .flatMapLatest { emptyFlow() }
    }

    private suspend fun getProperties() = getPropertiesUseCase.invoke()
}
