package com.freeletics.flowredux

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.freeletics.flowredux.Reducer
import com.freeletics.flowredux.SideEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

abstract class SubStateMachine<S : Any, A : Any, N : Any> {

    abstract val sideEffects: List<SideEffect<S, A>>

    abstract val reducer: Reducer<S, A>

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val navigationFlow = Channel<N>()

    fun navigation(): Flow<N> = navigationFlow.receiveAsFlow()

    protected inline fun <reified A : Any> createNavigationSideEffect(
        noinline navigationTransformer: (S, A) -> N?
    ): SideEffect<S, A> = { actions, getState ->
        actions.ofType(A::class)
            .throttleDistinct(1000)
            .mapNotNull { navigationTransformer(getState(), it) }
            .onEach {
                Log.d("SubStateMachine", "Receive action navigate to $it")
                navigationFlow.send(it)
            }
            .flatMapLatest { emptyFlow() }
    }
}
