package dvp.manga.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.BaseViewModel

class HomeViewModel internal constructor(app: Application, private val repository: MangaRepository) : BaseViewModel(app) {

    var mangas = MutableLiveData<List<Manga>>()

    init {
        getMangas()
    }

    private fun getMangas() {
        launch {
            mangas.value = repository.getMangas()
        }
    }

}
