package ch.com.findrealestate.features.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
            lastNavigationValue = it
            value = it
        }
    }
}

