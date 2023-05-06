package ch.com.findrealestate.features.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freeletics.flowredux.FlowReduxStateMachine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
abstract class FlowReduxViewModel<S : Any, A : Any, N : Any> constructor(
    private val stateMachine: FlowReduxStateMachine<S, A, N>
) : ViewModel() {
    private val stateflow = MutableStateFlow(stateMachine.initialState)

    var navigationValue: N? = null

    init {
        stateMachine.initStore()
        viewModelScope.launch {
            launch {
                stateMachine.state.collect {
                    stateflow.value = it
                }
            }
            launch {
                stateMachine.navigation.collect {
                    navigationValue = it
                    handleNavigation(it)
                }
            }
        }
    }

    abstract fun handleNavigation(navigation: N)

    @Composable
    fun rememberState() =
        stateflow.collectAsState()

    fun dispatch(action: A) = viewModelScope.launch {
        stateMachine.dispatch(action = action)
    }

    override fun onCleared() {
        super.onCleared()
        // handle dispose stateMachine
        stateMachine.dispose()
    }
}
