package dvp.app.azmanga.base

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*


inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    emit(Resource.Loading())
    delay(2000)
    val flow = if (shouldFetch(query().first())) {
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { Resource.Error(throwable, data = it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }
    emitAll(flow)
}

//@OptIn(ExperimentalCoroutinesApi::class)
//inline fun <ResultType, RequestType> networkBoundResource2(
//    crossinline query: () -> Flow<ResultType>,
//    crossinline fetch: suspend () -> RequestType,
//    crossinline saveFetchResult: suspend (RequestType) -> Unit,
//    crossinline shouldFetch: (ResultType) -> Boolean = { true }
//) = channelFlow {
//    val data = query().first()
//
//    if (shouldFetch(data)) {
//        val loading = launch {
//            query().collect { send(Resource.Loading(it)) }
//        }
//
//        try {
//            delay(2000)
//            saveFetchResult(fetch())
//            loading.cancel()
//            query().collect { send(Resource.Success(it)) }
//        } catch (t: Throwable) {
//            loading.cancel()
//            query().collect { send(Resource.Error(t, null)) }
//        }
//    } else {
//        query().collect { send(Resource.Success(it)) }
//    }
//}
