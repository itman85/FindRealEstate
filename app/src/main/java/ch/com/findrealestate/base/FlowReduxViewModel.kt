package ch.com.findrealestate.base

import android.util.Log
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
    protected val stateflow = MutableStateFlow(stateMachine.initialState)

    protected val navigationFlow = MutableStateFlow<N?>(null)

    var navigationValue: N? = null // current navigation value

    init {
        stateMachine.initStore()
        viewModelScope.launch {
            launch {
                stateMachine.state.collect {
                    Log.d("Phan1", "State collect $it")
                    stateflow.value = it
                }
            }
            launch {
                stateMachine.navigation().collect {
                    Log.d("Phan2", "Navigation collect $it")
                    navigationValue = it
                    navigationFlow.value = it
                    handleNavigation(it)
                }
            }
        }
    }

    abstract fun handleNavigation(navigation: N)

    @Composable
    fun rememberState() =
        stateflow.collectAsState() // in this way stateflow still in memory, then it will preserve previous state

    @Composable
    fun rememberNavigation() =
        navigationFlow.collectAsState() // If two items arrive in succession without delay, only the latter one will be received. this is default behaviour of state flow

    fun dispatch(action: A) = viewModelScope.launch {
        stateMachine.dispatch(action = action)
    }

    override fun onCleared() {
        super.onCleared()
        // handle dispose stateMachine
        stateMachine.dispose()
    }
}
