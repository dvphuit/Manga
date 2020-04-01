package dvp.manga.ui

import dvp.manga.data.model.Entity

sealed class Result {
    class Success(val data: List<Entity>, val hasNext: Boolean) : Result()
    object Empty : Result()
    class Error(val errMsg: String) : Result()
    object EmptyQuery : Result()
    object TerminalError : Result()
}