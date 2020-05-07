package dvp.manga.ui.home

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


    val top = repository.top
    val favourite = repository.favourite

    val lastUpdated = repository.lastUpdated

    val forBoy = repository.forBoy

    val forGirl = repository.forGirl

    val recyclerViewsState = mutableMapOf<Int, Parcelable?>()
}
