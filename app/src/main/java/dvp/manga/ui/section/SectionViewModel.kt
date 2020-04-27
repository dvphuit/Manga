package dvp.manga.ui.section

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.Result
import kotlinx.coroutines.*

class SectionViewModel internal constructor(
    private val repository: MangaRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var pageIndex = 1
    private val mangas = mutableListOf<Manga>()

    val state = MutableLiveData<Result>()

    fun initData(data: List<Manga>) {
        pageIndex++
        mangas.addAll(data)
        state.postValue(Result.Success(mangas, data.isNotEmpty()))
    }

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
