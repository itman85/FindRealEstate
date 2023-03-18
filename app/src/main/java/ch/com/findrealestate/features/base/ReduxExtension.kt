package ch.com.findrealestate.features.base

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import com.freeletics.flowredux.compose.rememberState
import com.freeletics.flowredux.dsl.StateMachine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.experimental.ExperimentalTypeInference
import kotlin.reflect.KClass

inline fun <A : Any, reified SubA : A> Flow<A>.ofType(clz: KClass<SubA>): Flow<SubA> =
    this.filter { it is SubA }.map { it as SubA }

fun <T> Flow<T>.throttleDistinct(periodMillis: Long): Flow<T> {
    require(periodMillis > 0) { "period should be positive" }
    return flow {
        var lastTime = 0L
        var lastValue: Any? = null
        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime >= periodMillis) {
                lastTime = currentTime
                lastValue = value
                emit(value)
            } else {
                // within time window, but different value
                if (lastValue != value) {
                    lastTime = currentTime
                    lastValue = value
                    emit(value)
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun <S : Any, A : Any, N : Any> BaseFlowReduxStateMachine<S, A, N>.rememberNavigation(): State<N?> {
    return produceState<N?>(initialValue = null, this) {
        navigation.collect {
            Log.d("Phan3", "navigation collect $it")
            value = it
        }
    }
}

@Composable
fun <T : R, R> Flow<T>.collectState(
    initial: R? = null,
    context: CoroutineContext = EmptyCoroutineContext
): State<R?> = produceState(initial, this, context) {
    if (context == EmptyCoroutineContext) {
        collect { value = it }
    } else withContext(context) {
        collect { value = it }
    }
}
