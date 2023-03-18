package ch.com.findrealestate.features.base

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
abstract class BaseStateViewModel<S : Any, A : Any, N : Any> constructor(
     private val stateMachine: BaseFlowReduxStateMachine<S, A, N>
) : ViewModel() {
    protected val stateflow = MutableStateFlow(stateMachine.initialState)
    protected val navigationFlow = MutableStateFlow<N?>(null)
    init {
        stateMachine.initStore()
        viewModelScope.launch {
            stateMachine.state.collect{
                Log.d("Phan1", "State collect $it")
                stateflow.value = it
            }
            stateMachine.navigation.collect{
                Log.d("Phan2", "Navigation collect $it")
                navigationFlow.value = it
            }
        }
    }

    @Composable
    fun rememberState() = stateflow.collectAsState() // in this way stateflow still in memory, then it will preserve previous state

    @Composable
    fun rememberNavigation() = stateMachine.rememberNavigation()//navigationFlow.collectState() todo should use collectState to preserve previous value of navigation

    fun dispatch(action: A) = viewModelScope.launch {
        stateMachine.dispatch(action = action)
    }

    override fun onCleared() {
        super.onCleared()
        // handle dispose stateMachine
        stateMachine.dispose()
    }
}
