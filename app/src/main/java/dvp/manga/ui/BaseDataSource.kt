package dvp.manga.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

/**
 * @author dvphu on 29,April,2020
 */

fun <T, L> responseLiveData(
    roomQueryToRetrieveData: () -> LiveData<T>,
    networkRequest: suspend () -> ResultData<L>,
    roomQueryToSaveData: suspend (L) -> Unit
): LiveData<ResultData<T>> = liveData(Dispatchers.IO) {
    emit(ResultData.loading(null))
    val source = roomQueryToRetrieveData().map { ResultData.success(it) }
    Log.d("TEST","from db")
    emitSource(source)
    when (val responseStatus = networkRequest()) {
        is ResultData.Success -> {
            roomQueryToSaveData(responseStatus.value)
        }

        is ResultData.Failure -> {
            emit(ResultData.failure(responseStatus.message))
            emitSource(source)
        }

        else -> {
            emit(ResultData.failure("Something went wrong, please try again later"))
            emitSource(source)
        }
    }

}