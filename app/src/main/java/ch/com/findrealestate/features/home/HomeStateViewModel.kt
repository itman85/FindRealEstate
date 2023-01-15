package ch.com.findrealestate.features.home

import ch.com.findrealestate.features.base.BaseStateViewModel
import ch.com.findrealestate.features.home.redux.HomeAction
import ch.com.findrealestate.features.home.redux.HomeState
import ch.com.findrealestate.features.home.redux.HomeStateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeStateViewModel @Inject constructor(stateMachine: HomeStateMachine) : BaseStateViewModel<HomeState, HomeAction>(stateMachine)

