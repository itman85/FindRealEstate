package com.freeletics.flowredux.dsl.util

import java.util.concurrent.atomic.AtomicInteger

class AtomicCounter constructor(initialValue: Int) {

    private val atomicInt: AtomicInteger = AtomicInteger(initialValue)

    fun get(): Int = atomicInt.get()
    fun incrementAndGet(): Int = atomicInt.incrementAndGet()
    fun decrementAndGet(): Int = atomicInt.decrementAndGet()
}
