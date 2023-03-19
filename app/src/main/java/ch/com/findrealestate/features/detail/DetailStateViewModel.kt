package ch.com.findrealestate.features.detail

import ch.com.findrealestate.features.base.BaseStateViewModel
import ch.com.findrealestate.features.detail.redux.DetailAction
import ch.com.findrealestate.features.detail.redux.DetailNavigation
import ch.com.findrealestate.features.detail.redux.DetailState
import ch.com.findrealestate.features.detail.redux.DetailStateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailStateViewModel @Inject constructor(stateMachine: DetailStateMachine) :
    BaseStateViewModel<DetailState, DetailAction, DetailNavigation>(stateMachine) {
    override fun handleNavigation(navigation: DetailNavigation) {
        // do something if needed
    }
}
