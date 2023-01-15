package ch.com.findrealestate.redux.home

import android.util.Log
import androidx.annotation.VisibleForTesting
import ch.com.findrealestate.domain.usecase.GetPropertiesUseCase
import ch.com.findrealestate.redux.base.BaseFlowReduxStateMachine
import ch.com.findrealestate.redux.base.ofType
import com.freeletics.flowredux.Reducer
import com.freeletics.flowredux.SideEffect
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@ViewModelScoped
class HomeStateMachine @Inject constructor(private val getPropertiesUseCase: GetPropertiesUseCase) :
    BaseFlowReduxStateMachine<HomeState, HomeAction>() {


    /*init {
        spec {
            // state in general
            inState {
                onEnter { state: State<HomeState> ->
                    dispatch(HomeAction.StartLoadData) // emit action
                    state.noChange()
                }
                // trigger action
                on { _: HomeAction.StartLoadData, state: State<HomeState> ->
                    state.override { HomeState.Loading }
                }
            }
            // handle for specific state
            inState<HomeState.Loading> {
                onEnter { state ->
                    try {
                        val result = getProperties()
                        state.override { HomeState.PropertiesLoaded(result) }
                    } catch (ex: Exception) {
                        state.override {
                            HomeState.Error(
                                ex.message ?: "Load properties list error"
                            )
                        }
                    }
                }
            }
        }
    }
     */


    override val initialState: HomeState = HomeState.Init

    override val initAction: HomeAction = HomeAction.StartLoadData

    override fun sideEffects(): List<SideEffect<HomeState, HomeAction>> =
        listOf(loadPropertiesSideEffect, favoriteClickSideEffect)

    override fun reducer(): Reducer<HomeState, HomeAction> = { state, action ->
        when (action) {
            is HomeAction.StartLoadData -> HomeState.Loading
            is HomeAction.DataLoadedError -> HomeState.Error(action.error ?: "")
            is HomeAction.DataLoaded -> HomeState.PropertiesLoaded(action.properties)
            else -> state
        }
    }

    @VisibleForTesting
    val favoriteClickSideEffect: SideEffect<HomeState, HomeAction> = { actions, getState ->
        actions.onEach { Log.d("Test1", it.toString()) }.filter { it is HomeAction.FavoriteClick }
            .map {
                it as HomeAction.StartLoadData
            }.flatMapLatest { // todo flatmap is cancel same to switchMap?
                flow {
                    try {
                        // load data
                        val properties = getProperties()
                        emit(HomeAction.DataLoaded(properties))
                    } catch (ex: Exception) {
                        emit(HomeAction.DataLoadedError(ex.message))
                    }
                }
            }
    }

    @VisibleForTesting
    val loadPropertiesSideEffect: SideEffect<HomeState, HomeAction> = { actions, _ ->
        actions.onEach { Log.d("Test2", it.toString()) }.ofType(HomeAction.StartLoadData::class)
            .onEach { Log.d("Test3", it.toString()) }
            .flatMapLatest {
                flow {
                    try {
                        // load data
                        val properties = getProperties()
                        emit(HomeAction.DataLoaded(properties))
                    } catch (ex: Exception) {
                        emit(HomeAction.DataLoadedError(ex.message))
                    }
                }
            }
    }

    private suspend fun getProperties() = getPropertiesUseCase.invoke()
}
