package ch.com.findrealestate.features.home.redux

import android.util.Log
import androidx.annotation.VisibleForTesting
import ch.com.findrealestate.domain.entity.Property
import ch.com.findrealestate.domain.usecase.GetPropertiesUseCase
import ch.com.findrealestate.features.home.HomeItem
import ch.com.findrealestate.features.home.components.similarproperties.redux.HomeSimilarPropertiesSubStateMachine
import ch.com.findrealestate.features.home.redux.sideeffects.FavoriteSideEffect
import com.freeletics.flowredux.CompositeStateMachine
import com.freeletics.flowredux.Reducer
import com.freeletics.flowredux.SideEffect
import com.freeletics.flowredux.SubStateMachine
import com.freeletics.flowredux.ofType
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
                HomeState.PropertiesListUpdated(state, items = items)
            }

            is HomeAction.FavoriteDialogYesClick,
            is HomeAction.ConfirmRemoveFavoriteNoClick -> HomeState.PropertiesListUpdated(
                state,
                items = state.items
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
            .onEach { Log.d("Phan", "Start Loading Properties") }
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
    val navigationSideEffect = createNavigationSideEffect<HomeBaseAction> { state, action ->
        when (action) {
            is HomeAction.PropertyClick -> HomeNavigation.OpenDetailScreen(action.propertyId)
            is HomeAction.FavoriteUpdated -> state.findPropertyItem(action.propertyId)
                ?.let { HomeNavigation.OpenDialogAddFavouriteSuccess(it.property) }

            is HomeAction.ConfirmRemoveFavorite -> HomeNavigation.OpenDialogRemovedFavouriteConfirmation(
                action.propertyId
            )

            else -> null
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val propertyClickSideEffect: SideEffect<HomeState, HomeBaseAction> = { actions, _ ->
        actions.ofType(HomeAction.PropertyClick::class)
            .onEach { Log.d("PropertyClickSE", it.toString()) }
            .flatMapLatest { emptyFlow() }
    }

    private suspend fun getProperties() = getPropertiesUseCase.invoke()
}
