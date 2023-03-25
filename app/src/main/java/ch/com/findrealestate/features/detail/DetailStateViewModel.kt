package ch.com.findrealestate.features.detail

import ch.com.findrealestate.features.base.BaseStateViewModel
import ch.com.findrealestate.features.detail.redux.DetailAction
import ch.com.findrealestate.features.detail.redux.DetailNavigation
import ch.com.findrealestate.features.detail.redux.DetailState
import ch.com.findrealestate.features.detail.redux.DetailStateMachine
import ch.com.findrealestate.features.home.HomeNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailStateViewModel @Inject constructor(stateMachine: DetailStateMachine) :
    BaseStateViewModel<DetailState, DetailAction, DetailNavigation>(stateMachine) {

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
            else -> {}
        }
    }
}
