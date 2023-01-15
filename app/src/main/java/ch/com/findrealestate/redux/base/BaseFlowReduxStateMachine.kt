package ch.com.findrealestate.redux.base

import com.freeletics.flowredux.Reducer
import com.freeletics.flowredux.SideEffect
import com.freeletics.flowredux.dsl.StateMachine
import com.freeletics.flowredux.dsl.util.AtomicCounter
import com.freeletics.flowredux.reduxStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseFlowReduxStateMachine<S:Any, A:Any>: StateMachine<S, A> {

    private val inputActions = Channel<A>()

    private lateinit var outputState: Flow<S>

    private val activeFlowCounter = AtomicCounter(0)

    protected abstract val initialState: S

    protected abstract val initAction: A

    protected abstract fun sideEffects(): List<SideEffect<S, A>>

    protected abstract fun reducer(): Reducer<S, A>

    fun initStore() {
        outputState = inputActions
            .receiveAsFlow()
            .onStart {
                emit(initAction)
            }
            .reduxStore(initialStateSupplier = {initialState}, sideEffects =  sideEffects(), reducer = reducer())
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


    override val state: Flow<S>
        get() = outputState

    override suspend fun dispatch(action: A) {
        if (activeFlowCounter.get() <= 0) {
            throw IllegalStateException(
                "Cannot dispatch action $action because state Flow of this " +
                        "FlowReduxStateMachine is not collected yet. " +
                        "Start collecting the state Flow before dispatching any action."
            )
        }
        inputActions.send(action)
    }
}
