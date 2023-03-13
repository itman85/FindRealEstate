package ch.com.findrealestate.features.base

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freeletics.flowredux.compose.rememberState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
abstract class BaseStateViewModel<State : Any, Action : Any, Navigation : Any> constructor(
    private val stateMachine: BaseFlowReduxStateMachine<State, Action, Navigation>
) : ViewModel() {
    init {
        stateMachine.initStore()
    }

    @Composable
    fun rememberState() = stateMachine.rememberState()

    @Composable
    fun rememberNavigation() = stateMachine.rememberNavigation()

    fun dispatch(action: Action) = viewModelScope.launch {
        stateMachine.dispatch(action = action)
    }

    fun dispose() {
        Log.d("ViewModel", "Dispose")
        // todo handle dispose stateMachine
        stateMachine.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        stateMachine.dispose()
    }
}
