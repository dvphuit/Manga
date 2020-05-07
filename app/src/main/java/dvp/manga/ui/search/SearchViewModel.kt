package dvp.manga.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.Result
import dvp.manga.ui.ResultData
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

data class QueryData(var key: String, var page: Int = 1)

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel(
    private val repository: MangaRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    companion object {
        const val SEARCH_DELAY_MS = 300L
        const val MIN_QUERY_LENGTH = 3
    }

    //region private variables
    private val data = mutableListOf<Manga>()
    private var preQuery = ""
    private var pageIndex = 1
    @ExperimentalCoroutinesApi
    private val queryChannel = BroadcastChannel<QueryData>(Channel.CONFLATED)

    private fun checkQuery(query: String) {
        if (preQuery != query) {
            preQuery = query
            pageIndex = 1
            data.clear()
        }
    }
    //endregion

    fun isQueryChanged(newQuery: String) = preQuery != newQuery

    fun submitQuery(query: String) {
        checkQuery(query)
        loadMore()
    }


    fun loadMore() {
        viewModelScope.launch {
            queryChannel.send(QueryData(preQuery, pageIndex))
        }
    }

    val state = queryChannel
        .asFlow()
        .debounce(SEARCH_DELAY_MS)
        .distinctUntilChanged()
        .mapLatest {
            try {
                if (it.key.length >= MIN_QUERY_LENGTH) {
                    when (val result = repository.searchManga(it.key, it.page)) {
                        is ResultData.Success -> {
                            if (result.value.isEmpty() && data.isEmpty()) {
                                pageIndex = 1
                                Result.Empty
                            } else {
                                pageIndex++
                                data.addAll(result.value)
                                Result.Success(data, result.value.isNotEmpty())
                            }
                        }
                        is ResultData.Failure -> {
                            Result.Error(result.message)
                        }
                        is ResultData.Loading -> {
                        }
                    }
                } else {
                    Result.EmptyQuery
                }
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
