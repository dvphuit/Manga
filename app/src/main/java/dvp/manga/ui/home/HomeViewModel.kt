package dvp.manga.ui.home

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class HomeViewModel internal constructor(
    private val repository: MangaRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    var isInitialized = false
    val state = MutableLiveData<Result>()

    var topMangas = liveData(Dispatchers.IO) {
        val response = repository.getTopManga()
        isInitialized = true
        emit(response)
    }

    val favourite = liveData(Dispatchers.IO) {
        val response = repository.getFavouriteMangas()
        emit(response)
    }

    val lastUpdated = liveData(Dispatchers.IO) {
        val response = repository.getLastUpdatedMangas()
        emit(response)
    }

    val forBoy = liveData(Dispatchers.IO) {
        val response = repository.getMangaForBoy()
        emit(response)
    }

    val forGirl = liveData(Dispatchers.IO) {
        val response = repository.getMangaForGirl()
        emit(response)
    }

    val recyclerViewsState = mutableMapOf<Int, Parcelable?>()
}
