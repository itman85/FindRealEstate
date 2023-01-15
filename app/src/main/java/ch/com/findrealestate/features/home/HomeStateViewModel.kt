package ch.com.findrealestate.features.home

import ch.com.findrealestate.features.base.BaseStateViewModel
import ch.com.findrealestate.features.home.redux.HomeAction
import ch.com.findrealestate.features.home.redux.HomeState
import ch.com.findrealestate.features.home.redux.HomeStateMachine
import ch.com.findrealestate.navigation.NavigationManager
import ch.com.findrealestate.navigation.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeStateViewModel @Inject constructor(
    stateMachine: HomeStateMachine,
    private val navigationManager: NavigationManager
) : BaseStateViewModel<HomeState, HomeAction>(stateMachine){
    fun navigateToDetail(){
        navigationManager.navigate(NavigationRoutes.detail)
    }
}

