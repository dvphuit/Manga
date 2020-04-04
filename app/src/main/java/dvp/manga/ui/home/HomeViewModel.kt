package dvp.manga.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.Result
import kotlinx.coroutines.*

class HomeViewModel internal constructor(
    private val repository: MangaRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var pageIndex = 1
    private val mangas = mutableListOf<Manga>()

    lateinit var top: List<Manga>
    var isInitialized = false
    val state = MutableLiveData<Result>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun loadMore() {
        viewModelScope.launch {
            state.value = getResult()
        }
    }

    private suspend fun getResult(): Result {
        try {
            val result = withContext(ioDispatcher) { repository.getMangas(pageIndex) }
            return if (result.isEmpty() && mangas.isEmpty()) {
                pageIndex = 1
                Result.Empty
            } else {
                pageIndex++
                isInitialized = true
                mangas.addAll(result)
                Result.Success(mangas, result.isNotEmpty())
            }
        } catch (e: Throwable) {
            if (e is CancellationException) {
                throw e
            } else {
                return Result.Error(e.localizedMessage!!)
            }
        }
    }
}
