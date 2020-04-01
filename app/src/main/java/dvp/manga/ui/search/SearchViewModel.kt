package dvp.manga.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import dvp.manga.data.model.Entity
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest

sealed class Result {
    class Success(val data: List<Entity>) : Result()
    object Empty : Result()
    class Error(val errMsg: String) : Result()
    object EmptyQuery : Result()
    object TerminalError : Result()
}

data class QueryModel(var key: String, var page: Int = 1)


class SearchViewModel(
    private val repository: MangaRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    companion object {
        const val SEARCH_DELAY_MS = 300L
        const val MIN_QUERY_LENGTH = 3
    }

    private val data = mutableListOf<Manga>()
    //    var query: QueryModel = QueryModel("", 1)
    private var preQuery = ""
    private var pageIndex = 1

    private fun handleData(newQuery: String, nextData: List<Manga>): List<Manga> {
        if (preQuery != newQuery) {
            preQuery = newQuery
            pageIndex = 1
            data.clear()
        }
        data.addAll(nextData)
        return data
    }

    fun isQueryChanged(newQuery: String): Boolean {
        return preQuery != newQuery
    }

    @ExperimentalCoroutinesApi
    internal val queryChannel = BroadcastChannel<QueryModel>(Channel.CONFLATED)

    val query = MutableLiveData<String>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    val state = query
        .asFlow()
        .debounce(SEARCH_DELAY_MS)
        .mapLatest {
            try {
                if (it.length >= MIN_QUERY_LENGTH) {
                    val result = withContext(ioDispatcher) {
                        repository.searchManga(it, pageIndex)
                    }
                    Log.d("TEST", "searching $it -- $pageIndex -- ${data.size}")

                    if (result.isEmpty()) {
                        Result.Empty
                    } else {
                        pageIndex++
                        Log.d("TEST", "search done $pageIndex")
                        Result.Success(handleData(it, result))
                    }
                } else Result.EmptyQuery
            } catch (e: Throwable) {
                if (e is CancellationException) {
                    Log.d(this.javaClass.simpleName, "search \"${it}\" was cancelled")
                    throw e
                } else {
                    Result.Error(e.localizedMessage!!)
                }

            }
        }
        .catch { emit(Result.TerminalError) }
        .asLiveData()

}
