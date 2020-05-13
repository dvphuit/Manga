package dvp.manga.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvp.manga.data.model.Manga
import dvp.manga.data.model.SectionRoute
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.FetchResult
import dvp.manga.ui.ResultData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreModel internal constructor(val repository: MangaRepository) : ViewModel() {

    private var pageIndex = 1
    private val mangas = mutableListOf<Manga>()
    private val state = MutableLiveData<FetchResult>()
    private lateinit var section: SectionRoute

    fun getState(section: SectionRoute): LiveData<FetchResult> {
        this.section = section
        return state
    }

    fun loadMore() {
        viewModelScope.launch {
            state.value = getResult()
        }
    }

    private suspend fun getResult(): FetchResult {
        try {
            return when (val result = withContext(Dispatchers.IO) { repository.fetchMangas(section, pageIndex) }) {
                is ResultData.Success -> {
                    return if (result.value.isEmpty() && mangas.isEmpty()) {
                        pageIndex = 1
                        FetchResult.Empty
                    } else {
                        pageIndex++
                        mangas.addAll(result.value)
                        FetchResult.Success(mangas, result.value.isNotEmpty())
                    }
                }
                is ResultData.Failure -> {
                    FetchResult.Error(result.message)
                }
                else -> FetchResult.Empty
            }
        } catch (e: Throwable) {
            if (e is CancellationException) {
                throw e
            } else {
                return FetchResult.Error(e.localizedMessage!!)
            }
        }
    }

}
