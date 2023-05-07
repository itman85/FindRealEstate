package ch.com.findrealestate.features.home

import android.util.Log
import ch.com.findrealestate.features.base.FlowReduxViewModel
import ch.com.findrealestate.features.home.redux.HomeAction
import ch.com.findrealestate.features.home.redux.HomeNavigation
import ch.com.findrealestate.features.home.redux.HomeState
import ch.com.findrealestate.features.home.redux.HomeStateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeStateViewModel @Inject constructor(
    stateMachine: HomeStateMachine
) : FlowReduxViewModel<HomeState, HomeAction, HomeNavigation>(stateMachine) {

    private lateinit var navigator: HomeNavigator
    fun setNavigator(navigator: HomeNavigator){
        this.navigator = navigator
    }

    override fun handleNavigation(navigation: HomeNavigation) {
        // this help to prevent production crash, as we get crash in testing first
        if(!this::navigator.isInitialized){
            error("HomeNavigator is not initialized yet ")
        }
        when(navigation){
            is HomeNavigation.OpenDetailScreen -> navigator.navigateToDetail(navigation.propertyId)
            else -> {
                Log.i("HomeVM", "No navigation, just stay Home screen")
            }
        }
    }
}

