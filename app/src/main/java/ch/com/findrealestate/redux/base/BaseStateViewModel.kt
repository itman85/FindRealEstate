package ch.com.findrealestate.redux.base

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freeletics.flowredux.compose.rememberState
import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
abstract class BaseStateViewModel<State:Any, Action:Any> constructor(
    private val stateMachine: BaseFlowReduxStateMachine<State,Action>
): ViewModel(){
    init {
        stateMachine.initStore()
    }
    @Composable
    fun rememberState() = stateMachine.rememberState()

    fun dispatch(action: Action) = viewModelScope.launch {
        stateMachine.dispatch(action = action)
    }
}
