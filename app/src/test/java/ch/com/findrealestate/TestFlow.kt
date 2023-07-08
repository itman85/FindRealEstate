package ch.com.findrealestate

import app.cash.turbine.test
import io.kotlintest.shouldBe
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import java.util.concurrent.CountDownLatch

// https://proandroiddev.com/from-rxjava-to-kotlin-flow-throttling-ed1778847619
@OptIn(ExperimentalCoroutinesApi::class)
@Ignore
class TestFlow {
    private fun myFlow(): Flow<Int> {
        return flow {
            emit(1)
            delay(90)
            emit(2)
            delay(90)
            emit(3)
            delay(1010)
            emit(4)
            delay(1010)
            emit(5)
            delay(2000)
            emit(6)
            delay(90)
            emit(7)
            delay(1010)
            emit(8)
            delay(80)
            emit(9)
        }
    }

    private fun myFlow1(): Flow<Int> {
        return flow {
            emit(1)
            delay(500)
            emit(1)
            delay(90)
            emit(3)
            delay(100)
            emit(3)
            delay(100)
            emit(2)
        }
    }

    private fun testFlow(operator: Flow<Int>.() -> Flow<Int>) {

        val latch = CountDownLatch(1)
        val result = StringBuffer()

        CoroutineScope(Job() + Dispatchers.Default).launch {
            myFlow()
                .operator()
                .onCompletion { latch.countDown() }
                .collect { result.append(it).append(" ") }
        }

        latch.await()
        println("$result")
    }

    private fun testFlow1(operator: Flow<Int>.() -> Flow<Int>) {

        val latch = CountDownLatch(1)
        val result = StringBuffer()

        CoroutineScope(Job() + Dispatchers.Default).launch {
            myFlow1()
                .operator()
                .onCompletion { latch.countDown() }
                .collect { result.append(it).append(" ") }
        }

        latch.await()
        println("$result")
    }

    fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
        require(periodMillis > 0) { "period should be positive" }
        return flow {
            var lastTime = 0L
            collect { value ->
                println("collect $value")
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastTime >= periodMillis) {
                    lastTime = currentTime
                    println("emit $value")
                    emit(value)
                }
            }
        }
    }

    fun <T> Flow<T>.throttleDistinct(periodMillis: Long): Flow<T> {
        require(periodMillis > 0) { "period should be positive" }
        return flow {
            var lastTime = 0L
            var lastValue: Any? = null
            collect { value ->
                println("collect $value")
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastTime >= periodMillis) {
                    lastTime = currentTime
                    lastValue = value
                    println("emit $value")
                    emit(value)
                }else{
                    // within time window, but different value
                    if(lastValue != value) {
                        lastTime = currentTime
                        lastValue = value
                        println("emit $value")
                        emit(value)
                    }
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun <T> Flow<T>.throttleLatest(periodMillis: Long): Flow<T> {
        require(periodMillis > 0) { "period should be positive" }

        return channelFlow {
            val done = Any()
            val values = produce(capacity = Channel.CONFLATED) {
                collect { value ->
                    send(value)
                    println("send $value")
                }
            }

            var lastValue: Any? = null
            val ticker = Ticker(periodMillis)
            while (lastValue !== done) {
                select {
                    values.onReceive {
                        println("receive $it")
                        if (it == null) {
                            ticker.cancel()
                            lastValue = done
                        } else {
                            lastValue = it
                            if (!ticker.isStarted) {
                                ticker.start(this@channelFlow)
                            }
                        }

                    }

                    ticker.getTicker().onReceive {
                        if (lastValue !== null) {
                            val value = lastValue
                            lastValue = null
                            println("emit $value")
                            send(value as T)
                        } else {
                            ticker.stop()
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testFlowsDebounce() {

        testFlow { debounce(1000) } // 3 4 5 7 9

    }

    @Test
    fun testFlowsThrottleFirst() {

        testFlow { throttleFirst(1000) } // 1 4 5 6 8

    }

    @Test
    fun testFlowsThrottleLast() {

        // sample function same to ThrottleLast in RX, it will NOT immediately emit the first item, it will always wait for the time window to pass and then emit the last value from that time window (if any)
        // so in this case  value will not be emitted, because stream finished before the time window pass
        testFlow { sample(1000) } // 3 4 5 7
    }

    @Test
    fun testFlowsThrottleLatest() {

        // ThrottleLatest will immediately emit the first item and after that it will behave the same as ThrottleLast.
        testFlow { throttleLatest(1000) } // 1 3 4 5 6 7
    }

    @Test
    fun testFlowsThrottleDistinct() {

        testFlow1 { throttleDistinct(1000) } // 1,2,3

    }

    @Test
    fun testFlow() = runTest {

        flowOf("one", "two").test {
            awaitItem().shouldBe("one")
            awaitItem().shouldBe("two")
            awaitComplete()
        }

    }

}

class Ticker(private val delay: Long) {

    private var channel: ReceiveChannel<Unit> = Channel()

    var isStarted: Boolean = false
        private set

    fun getTicker(): ReceiveChannel<Unit> {
        return channel
    }

    fun start(scope: CoroutineScope) {
        isStarted = true
        channel.cancel()
        channel = scope.produce(capacity = 0) {
            while (true) {
                channel.send(Unit)
                delay(delay)
            }
        }
    }

    fun stop() {
        isStarted = false
        channel.cancel()
        channel = Channel()
    }

    fun cancel() {
        isStarted = false
        channel.cancel()
    }
}
