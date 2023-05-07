package ch.com.findrealestate.features.detail.redux

import androidx.annotation.VisibleForTesting
import ch.com.findrealestate.domain.usecase.GetPropertyDetail
import com.freeletics.flowredux.FlowReduxStateMachine
import com.freeletics.flowredux.Reducer
import com.freeletics.flowredux.SideEffect
import com.freeletics.flowredux.ofType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(FlowPreview::class)
@ExperimentalCoroutinesApi
class DetailStateMachine @Inject constructor(private val getPropertyDetail: GetPropertyDetail) :
    FlowReduxStateMachine<DetailState, DetailAction, DetailNavigation>() {


    @FlowPreview
    override val initialState: DetailState = DetailState.Init

    override fun sideEffects(): List<SideEffect<DetailState, DetailAction>> =
        listOf(loadPropertyDetailSideEffect, navigationSideEffect, screenResumeSideEffect)

    @VisibleForTesting
    val loadPropertyDetailSideEffect: SideEffect<DetailState, DetailAction> = { actions, _ ->
        actions.ofType(DetailAction.LoadDetailData::class)
            .flatMapLatest { action ->
                flow {
                    try {
                        emit(DetailAction.DetailDataLoading)
                        val propertyDetail = getPropertyDetail.invoke(action.propertyId)
                        if (propertyDetail != null)
                            emit(DetailAction.DetailDataLoaded(propertyDetail))
                        else
                            emit(DetailAction.DetailDataLoadedError("Cannot find property detail for id ${action.propertyId}"))
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        emit(
                            DetailAction.DetailDataLoadedError(
                                ex.message ?: "Load property detail error"
                            )
                        )
                    }

                }
            }
    }

    @VisibleForTesting
    val navigationSideEffect = createNavigationSideEffect<DetailAction> { state, action ->
        when (action) {
            is DetailAction.OpenPropertyWebsiteClick -> DetailNavigation.OpenPropertyWebsite("https://www.homegate.ch/en/${state.propertyId}")
            else -> null
        }
    }

    @VisibleForTesting
    val screenResumeSideEffect: SideEffect<DetailState, DetailAction> = { actions, _ ->
        actions.ofType(DetailAction.ScreenResumed::class)
            .onEach { navigationFlow.send(DetailNavigation.NoNavigation) } // reset navigation
            .flatMapLatest {
                when (it.lastNavigation) {
                    is DetailNavigation.OpenPropertyWebsite -> flow { emit(DetailAction.ToggleShowPropertyInfoBottomSheet(true)) }
                    else -> emptyFlow()
                }
            }
    }

    @FlowPreview
    override fun reducer(): Reducer<DetailState, DetailAction> = { state, action ->
        when (action) {
            is DetailAction.LoadDetailData -> state.copy(propertyId = action.propertyId)
            is DetailAction.DetailDataLoading -> state.copy(isLoading = true)
            is DetailAction.DetailDataLoaded -> state.copy(
                detailProperty = action.data,
                isLoading = false,
                errorMsg = null
            )
            is DetailAction.DetailDataLoadedError -> state.copy(
                isLoading = false,
                errorMsg = action.errorMsg
            )
            is DetailAction.ToggleShowPropertyInfoBottomSheet -> state.copy(isShowInfoBottomSheet = action.isShow)
            else -> state
        }
    }
}
