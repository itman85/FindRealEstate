package com.freeletics.flowredux

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow


@FlowPreview
@ExperimentalCoroutinesApi
abstract class CompositeStateMachine<S : Any, A : Any, N : Any> :
    FlowReduxStateMachine<S, A, N>() {

    abstract val subStateMachines: List<SubStateMachine<S, A, N>>

    override fun sideEffects(): List<SideEffect<S, A>> = createSubStateMachineSideEffects()

    override fun reducer(): Reducer<S, A> = createSubStateMachineReducers()

    override fun navigation(): Flow<N> =
        listOf(createSubStateMachineNavigation(), navigationFlow.receiveAsFlow()).merge()

    private fun createSubStateMachineNavigation(): Flow<N> {
        return subStateMachines.map {
            it.navigation()
        }.merge()
    }

    protected fun createSubStateMachineSideEffects(vararg additionalSideEffect: SideEffect<S, A>) =
        mutableListOf<SideEffect<S, A>>().apply {
            subStateMachines.forEach {
                addAll(it.sideEffects)
            }
            addAll(additionalSideEffect)
        }

    /**
     * This reducer is a chain of sub-reducers from sub state machines
     */
    protected fun createSubStateMachineReducers(vararg additionalReducers: Reducer<S, A>): Reducer<S, A> =
        CompositeReducer(
            reducers = subStateMachines.map {
                @Suppress("UNCHECKED_CAST")
                it.reducer
            } + additionalReducers
        )

    private class CompositeReducer<S, A>(private val reducers: List<Reducer<S, A>>) :
        Reducer<S, A> {

        override fun invoke(currentState: S, newAction: A): S {
            Log.v(javaClass.simpleName, "reducer action $newAction")
            var newState: S = currentState
            reducers.forEach {
                try {
                    newState = it.invoke(newState, newAction)
                } catch (ignored: ClassCastException) {
                    ignored.printStackTrace()
                }
            }
            return newState
        }
    }
}
