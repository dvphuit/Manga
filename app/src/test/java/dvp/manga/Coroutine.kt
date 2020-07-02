package dvp.manga

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

/**
 * @author dvphu on 01,July,2020
 */

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class Coroutine {


    suspend fun <T> ReceiveChannel<T>.debounce(
        wait: Long = 550,
        context: CoroutineContext = Dispatchers.Default
    ): ReceiveChannel<T> {
        return withContext(context) {
            produce {
                var lastTimeout: Job? = null
                consumeEach {
                    lastTimeout?.cancel()
                    lastTimeout = launch {
                        delay(wait)
                        println("debounce $this $it")
                        send(it)
                    }
                }
                lastTimeout?.join()
            }
        }
    }

    suspend fun <T> ReceiveChannel<T>.throttle(
        wait: Long = 200,
        context: CoroutineContext = Dispatchers.Default
    ): ReceiveChannel<T> {
        return withContext(context) {
            produce {
                var nextTime = 0L
                consumeEach {
//                    println("throttle $this $it")
                    val curTime = System.currentTimeMillis()
                    if (curTime >= nextTime) {
                        nextTime = curTime + wait
                        send(it)
                    }
                }
            }
        }
    }

    @Test
    fun main() = runBlocking {
        println("start test")
        val channel = produce<Int> {
            (0..100).forEach {
                println("send $this $it")
                send(it)
                delay(10)
            }
        }
        channel.debounce().consumeEach {
            println("consume $this  $it")
        }
    }

}