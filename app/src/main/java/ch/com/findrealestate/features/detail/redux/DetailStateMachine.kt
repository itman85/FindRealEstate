package ch.com.findrealestate.features.detail.redux

import androidx.annotation.VisibleForTesting
import ch.com.findrealestate.domain.usecase.GetPropertyDetail
import ch.com.findrealestate.features.base.BaseFlowReduxStateMachine
import ch.com.findrealestate.features.base.ofType
import com.freeletics.flowredux.Reducer
import com.freeletics.flowredux.SideEffect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DetailStateMachine @Inject constructor(private val getPropertyDetail: GetPropertyDetail) :
    BaseFlowReduxStateMachine<DetailState, DetailAction, DetailNavigation>() {


    @FlowPreview
    override val initialState: DetailState = DetailState.Init

    override fun sideEffects(): List<SideEffect<DetailState, DetailAction>> =
        listOf(loadPropertyDetailSideEffect)

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

    override fun reducer(): Reducer<DetailState, DetailAction> = { state, action ->
        when (action) {
            is DetailAction.DetailDataLoading -> DetailState.DetailDataLoading
            is DetailAction.DetailDataLoaded -> DetailState.DetailDataLoaded(action.data)
            is DetailAction.DetailDataLoadedError -> DetailState.DetailDataLoadedError(action.errorMsg)
            else -> state
        }
    }
}
