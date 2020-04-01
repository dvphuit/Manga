package dvp.manga.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dvp.manga.data.model.Entity
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest

sealed class Result {
    class Success(val data: List<Entity>) : Result()
    class Error(val errMsg: String) : Result()
    object EmptyQuery : Result()
    object TerminalError : Result()
}

data class QueryModel(val query: String, val page: Int = 1)


class SearchViewModel(
    private val repository: MangaRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    companion object {
        const val SEARCH_DELAY_MS = 300L
        const val MIN_QUERY_LENGTH = 3
    }

    private val data = mutableListOf<Manga>()
    private var preQuery = ""
    private fun handleData(newQuery: String, nextData: List<Manga>): List<Manga> {
        if (preQuery != newQuery) {
            preQuery = newQuery
            data.clear()
        }
        data.addAll(nextData)
        return data
    }

    @ExperimentalCoroutinesApi
    internal val query = BroadcastChannel<QueryModel>(Channel.CONFLATED)

    @FlowPreview
    @ExperimentalCoroutinesApi
    val state = query
        .asFlow()
//        .debounce(SEARCH_DELAY_MS)
        .mapLatest {
            try {
                if (it.query.length >= MIN_QUERY_LENGTH) {
                    val result = withContext(ioDispatcher) {
                        repository.searchManga(it.query, it.page)
                    }
                    Result.Success(handleData(it.query, result))
                } else Result.EmptyQuery
            } catch (e: Throwable) {
                if (e is CancellationException) {
                    Log.d(this.javaClass.simpleName, "search \"${it.query}\" was cancelled")
                    throw e
                } else {
                    Result.Error(e.localizedMessage!!)
                }

            }
        }.catch { emit(Result.TerminalError) }.asLiveData()

}
