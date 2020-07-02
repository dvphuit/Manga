package dvp.manga.data.local

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlin.coroutines.CoroutineContext

/**
 * @author dvphu on 22,May,2020
 */

@OptIn(ExperimentalCoroutinesApi::class)
class DaoManager {

    private var receiveChannel: ReceiveChannel<Deferred<(suspend () -> Unit)>> = Channel(capacity = 1)

    fun addQueue(query: (suspend () -> Unit)) = runBlocking {
        launch {
            receiveChannel = produce {
                send(async { query })
            }
            receiveChannel.debounce().consumeEach {
                it.await().invoke()
            }
        }
    }

    private suspend fun <T> ReceiveChannel<Deferred<T>>.debounce(
        wait: Long = 200,
        context: CoroutineContext = Dispatchers.IO
    ): ReceiveChannel<Deferred<T>> = withContext(context) {
        produce(capacity = 1) {
            var lastTimeout: Job? = null
            consumeEach {
                lastTimeout?.cancel()
                lastTimeout = launch {
                    delay(wait)
                    send(it)
                }
            }
            lastTimeout?.join()
        }
    }

    companion object {
        @Volatile
        private var instance: DaoManager? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: DaoManager().also { instance = it }
        }
    }
}

