package ch.com.findrealestate.features.detail

import ch.com.findrealestate.base.FlowReduxViewModel
import ch.com.findrealestate.features.detail.redux.DetailAction
import ch.com.findrealestate.features.detail.redux.DetailNavigation
import ch.com.findrealestate.features.detail.redux.DetailState
import ch.com.findrealestate.features.detail.redux.DetailStateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailStateViewModel @Inject constructor(stateMachine: DetailStateMachine) :
    FlowReduxViewModel<DetailState, DetailAction, DetailNavigation>(stateMachine) {

    private lateinit var navigator: DetailNavigator
    fun setNavigator(navigator: DetailNavigator) {
        this.navigator = navigator
    }

    // this way helps to prevent reload data when re-enter composition
    fun startLoadData(propertyId: String) {
        if (stateflow.value == DetailState.Init)
            dispatch(DetailAction.LoadDetailData(propertyId))
    }

    override fun handleNavigation(navigation: DetailNavigation) {
        // this helps to know if navigator is not initialized
        if (!this::navigator.isInitialized) {
            error("DetailNavigator is not initialized")
        }
        when (navigation) {
            is DetailNavigation.OpenPropertyWebsite -> navigator.navigateOpenChromeTab(navigation.url)
            else -> {}
        }
    }
}
