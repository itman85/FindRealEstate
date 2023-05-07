package com.freeletics.flowredux

import android.util.Log
import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
abstract class FlowReduxStateMachine<S : Any, A : Any, N : Any> : StateMachine<S, A> {

    private val inputActions = Channel<A>()

    private lateinit var outputState: Flow<S>

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val navigationFlow = Channel<N>()

    private val activeFlowCounter = AtomicCounter(0)

    abstract val initialState: S

    protected open val initAction: A? = null
    protected abstract fun sideEffects(): List<SideEffect<S, A>>

    @VisibleForTesting
    protected abstract fun reducer(): Reducer<S, A>

    fun initStore() {
        outputState = inputActions
            .receiveAsFlow()
            .onStart {
                initAction?.let { emit(it) }
            }
            .reduxStore(
                initialStateSupplier = { initialState },
                sideEffects = sideEffects(),
                reducer = reducer()
            )
            .distinctUntilChanged { old, new -> old === new } // distinct until not the same object reference.
            .onStart {
                if (activeFlowCounter.incrementAndGet() > 1) {
                    throw IllegalStateException(
                        "Can not collect state more than once at the same time. Make sure the" +
                                "previous collection is cancelled before starting a new one. " +
                                "Collecting state in parallel would lead to subtle bugs."
                    )
                }
            }
            .onCompletion {
                activeFlowCounter.decrementAndGet()
            }
    }

    fun dispose() {
        inputActions.close()
        navigationFlow.close()
    }


    override val state: Flow<S>
        get() = outputState

    open fun navigation(): Flow<N> = navigationFlow.receiveAsFlow()

    override suspend fun dispatch(action: A) {
        // todo check this needed?
         if (activeFlowCounter.get() <= 0) {
             throw IllegalStateException(
                 "Cannot dispatch action $action because state Flow of this " +
                         "FlowReduxStateMachine is not collected yet. " +
                         "Start collecting the state Flow before dispatching any action."
             )
         }
        inputActions.send(action)
    }

    protected inline fun <reified A : Any> createNavigationSideEffect(
        noinline navigationTransformer: (S, A) -> N?
    ): SideEffect<S, A> = { actions, getState ->
        actions.ofType(A::class)
            .throttleDistinct(1000)
            .mapNotNull { navigationTransformer(getState(), it) }
            .onEach {
                Log.d("FlowRedux", "Receive action navigate to $it")
                navigationFlow.send(it)
            }
            .flatMapLatest { emptyFlow() }
    }
}



