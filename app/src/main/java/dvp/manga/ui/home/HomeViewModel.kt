package dvp.manga.ui.home

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvp.manga.data.model.SectionRoute
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.FetchResult

class HomeViewModel internal constructor(repository: MangaRepository) : ViewModel() {

    var isInitialized = false

    val state = MutableLiveData<FetchResult>()

    val top = repository.top

    val favourite = repository.getMangas(SectionRoute.FAVOURITE)

    val lastUpdated = repository.getMangas(SectionRoute.LAST_UPDATE)

    val forBoy = repository.getMangas(SectionRoute.FOR_BOY)

    val forGirl = repository.getMangas(SectionRoute.FOR_GIRL)

    val recyclerViewsState = mutableMapOf<Int, Parcelable?>()
}
