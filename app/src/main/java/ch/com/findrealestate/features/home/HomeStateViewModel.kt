package ch.com.findrealestate.features.home

import android.util.Log
import ch.com.findrealestate.base.BaseStateViewModel
import ch.com.findrealestate.features.home.components.similarproperties.redux.HomeSimilarPropertiesNavigation
import ch.com.findrealestate.features.home.redux.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeStateViewModel @Inject constructor(
    stateMachine: HomeStateMachine
) : BaseStateViewModel<HomeState, HomeBaseAction, HomeBaseNavigation>(stateMachine) {
    fun startLoadData() {
        // use this way or set initAction for state machine
        if (stateflow.value == HomeState.Init)
            dispatch(HomeAction.StartLoadData)
    }

    private lateinit var navigator: HomeNavigator
    fun setNavigator(navigator: HomeNavigator) {
        this.navigator = navigator
    }

    override fun handleNavigation(navigation: HomeBaseNavigation) {
        // this help to prevent production crash, as we get crash in testing first
        if (!this::navigator.isInitialized) {
            error("HomeNavigator is not initialized yet ")
        }
        Log.d("Phan2", "navigation change $navigation")
        when (navigation) {
            is HomeNavigation.OpenDetailScreen -> navigator.navigateToDetail(navigation.propertyId)
            is HomeSimilarPropertiesNavigation.OpenSimilarPropertyDetail -> navigator.navigateToDetail(
                navigation.propertyId
            )
            else -> {
                Log.i("Phan2", "No navigation, just stay Home screen")
            }
        }
    }
}

