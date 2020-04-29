package dvp.manga.ui

import dvp.manga.data.model.Entity

sealed class Result {
    class Success(val data: List<Entity>, val hasNext: Boolean) : Result()
    object Empty : Result()
    class Error(val errMsg: String) : Result()
    object EmptyQuery : Result()
    object TerminalError : Result()
}

sealed class ResultData<out T> {

    data class Success<out T>(val value: T) : ResultData<T>()

    data class Failure<out T>(val message: String) : ResultData<T>()

    data class Loading<out T>(val value: T? = null) : ResultData<T>()

    companion object {

        fun <T> loading(value: T?): ResultData<T> = Loading(value)

        fun <T> success(value: T): ResultData<T> = Success(value)

        fun <T> failure(error_msg: String): ResultData<T> = Failure(error_msg)

    }

}