package com.freeletics.flowredux

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.produceState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
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


