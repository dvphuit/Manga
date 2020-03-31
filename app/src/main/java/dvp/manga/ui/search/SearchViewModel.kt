package dvp.manga.ui.search

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest

class SearchViewModel2 internal constructor(app: Application, private val repository: MangaRepository) : BaseViewModel(app) {

    var mangas = mutableListOf<Manga>()

    lateinit var top: List<Manga>

    fun searchManga(query: String, page: Int) {
        if (query.length < 2) return
        launch {
            //            mangas.clear()
            mangas.addAll(repository.searchManga(query, page))
            return@launch mangas.isNotEmpty()
        }
    }


}


sealed class SearchResult
class ValidResult(val result: List<Manga>) : SearchResult()
object EmptyResult : SearchResult()
object EmptyQuery : SearchResult()
class ErrorResult(val e: Throwable) : SearchResult()
object TerminalError : SearchResult()
data class QueryModel(val query: String, val page: Int = 1)

class SearchViewModel(
    private val repository: MangaRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    companion object {
        const val SEARCH_DELAY_MS = 300L
        const val MIN_QUERY_LENGTH = 3
    }

    @ExperimentalCoroutinesApi
    @VisibleForTesting
    internal val queryChannel = BroadcastChannel<QueryModel>(Channel.CONFLATED)

    @FlowPreview
    @ExperimentalCoroutinesApi
    @VisibleForTesting
    internal val internalSearchResult = queryChannel
        .asFlow()
        .debounce(SEARCH_DELAY_MS)
        .mapLatest {
            try {
                if (it.query.length >= MIN_QUERY_LENGTH) {
                    val result = withContext(ioDispatcher) { repository.searchManga(it.query, it.page) }
                    println("Search result: ${result.size} hits")
                    if (result.isNotEmpty()) {
                        ValidResult(result)
                    } else {
                        EmptyResult
                    }
                } else {
                    EmptyQuery
                }
            } catch (e: Throwable) {
                if (e is CancellationException) {
                    println("Search was cancelled!")
                    throw e
                } else {
                    ErrorResult(e)
                }
            }
        }.catch {
            emit(TerminalError)
        }

    @ExperimentalCoroutinesApi
    @FlowPreview
    var searchResult = internalSearchResult.asLiveData()

//    @FlowPreview
//    @ExperimentalCoroutinesApi
//    fun loadMore(page: Int) {
//        viewModelScope.launch {
//            val result = withContext(ioDispatcher) { repository.searchManga(this@SearchViewModel.query, page) }
//            searchResult.value = ValidResult(result)
//        }
//    }

    class Factory(private val repo: MangaRepository, private val dispatcher: CoroutineDispatcher) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchViewModel(repo, dispatcher) as T
        }
    }
}
