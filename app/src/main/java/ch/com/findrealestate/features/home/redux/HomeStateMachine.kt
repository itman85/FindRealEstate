package ch.com.findrealestate.features.home.redux

import android.util.Log
import androidx.annotation.VisibleForTesting
import ch.com.findrealestate.domain.usecase.GetPropertiesUseCase
import ch.com.findrealestate.features.base.BaseFlowReduxStateMachine
import ch.com.findrealestate.features.base.ofType
import ch.com.findrealestate.features.home.redux.sideeffects.FavoriteSideEffect
import com.freeletics.flowredux.Reducer
import com.freeletics.flowredux.SideEffect
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
    private val favoriteSideEffect: FavoriteSideEffect
) : BaseFlowReduxStateMachine<HomeState, HomeAction, HomeNavigation>() {

    override val initialState: HomeState = HomeState.Init

    override val initAction: HomeAction = HomeAction.StartLoadData

    override fun sideEffects(): List<SideEffect<HomeState, HomeAction>> =
        listOf(
            loadPropertiesSideEffect,
            favoriteSideEffect,
            propertyClickSideEffect,
            navigationSideEffect
        )

    override fun reducer(): Reducer<HomeState, HomeAction> = { state, action ->
        when (action) {
            is HomeAction.StartLoadData -> HomeState.Loading
            is HomeAction.DataLoadedError -> HomeState.Error(action.error ?: "")
            is HomeAction.DataLoaded -> HomeState.PropertiesLoaded(action.properties)
            is HomeAction.FavoriteUpdated -> {
                HomeState.PropertiesListUpdated(properties = state.properties.map {
                    if (it.id == action.propertyId)
                        it.copy(isFavorite = action.isFavorite)
                    else it
                })
            }
            else -> state
        }
    }

    @VisibleForTesting
    val loadPropertiesSideEffect: SideEffect<HomeState, HomeAction> = { actions, _ ->
        actions.ofType(HomeAction.StartLoadData::class)
            .onEach { Log.d("LoadPropertiesSE", it.toString()) }
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


    @VisibleForTesting
    val navigationSideEffect = createNavigationSideEffect<HomeAction> { _, action ->
        when (action) {
            is HomeAction.PropertyClick -> HomeNavigation.OpenDetailScreen(action.propertyId)
            else -> null
        }
    }

    @VisibleForTesting
    val propertyClickSideEffect: SideEffect<HomeState, HomeAction> = { actions, _ ->
        actions.ofType(HomeAction.PropertyClick::class)
            .onEach { Log.d("PropertyClickSE", it.toString()) }
            .flatMapLatest { emptyFlow() }
    }

    private suspend fun getProperties() = getPropertiesUseCase.invoke()
}
