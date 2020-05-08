package dvp.manga.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

/**
 * @author dvphu on 29,April,2020
 */

fun <T> responseLiveData(
    dbQuery: () -> LiveData<T>,
    netCall: suspend () -> ResultData<T>,
    saveNetCall: suspend (T) -> Unit
): LiveData<ResultData<T>> = liveData(Dispatchers.IO) {
    emit(ResultData.loading(null))
    val source = dbQuery().map { ResultData.success(it) }
    emitSource(source)
    when (val res = netCall()) {
        is ResultData.Success -> {
            saveNetCall(res.value)
            emit(res)
        }
        is ResultData.Failure -> {
            emit(ResultData.failure(res.message))
            emitSource(source)
        }
        else -> {
            emit(ResultData.failure("Something went wrong, please try again later"))
            emitSource(source)
        }
    }
}

suspend fun <T> fetchData(
    netCall: suspend () -> ResultData<T>,
    saveNetCall: suspend (T) -> Unit
): ResultData<T> = coroutineScope {
    when (val res = netCall()) {
        is ResultData.Success -> {
            saveNetCall(res.value)
            return@coroutineScope res
        }
        is ResultData.Failure -> {
            return@coroutineScope ResultData.failure<T>(res.message)
        }
        else -> {
            return@coroutineScope ResultData.failure<T>("Something went wrong, please try again later")
        }
    }
}