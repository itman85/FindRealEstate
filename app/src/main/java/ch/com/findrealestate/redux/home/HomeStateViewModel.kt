package ch.com.findrealestate.redux.home

import ch.com.findrealestate.redux.base.BaseStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeStateViewModel @Inject constructor(stateMachine: HomeStateMachine) : BaseStateViewModel<HomeState,HomeAction>(stateMachine)

