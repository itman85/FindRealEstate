package ch.com.findrealestate.features.detail

import android.util.Log
import ch.com.findrealestate.features.base.FlowReduxViewModel
import ch.com.findrealestate.features.detail.redux.DetailAction
import ch.com.findrealestate.features.detail.redux.DetailNavigation
import ch.com.findrealestate.features.detail.redux.DetailState
import ch.com.findrealestate.features.detail.redux.DetailStateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class DetailStateViewModel @Inject constructor(stateMachine: DetailStateMachine) :
    FlowReduxViewModel<DetailState, DetailAction, DetailNavigation>(stateMachine) {

    private lateinit var navigator: DetailNavigator
    fun setNavigator(navigator: DetailNavigator){
        this.navigator = navigator
    }
    override fun handleNavigation(navigation: DetailNavigation) {
        // this help to prevent production crash, as we get crash in testing first
        if(!this::navigator.isInitialized){
            error("DetailNavigator is not initialized yet ")
        }
        when(navigation){
            is DetailNavigation.OpenPropertyWebsite -> navigator.navigateOpenChromeTab(navigation.url)
            else -> {
                Log.i("DetailVM", "No navigation, just stay Detail screen")
            }
        }
    }
}
