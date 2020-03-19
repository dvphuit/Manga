package dvp.manga.ui.home

import android.app.Application
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.BaseViewModel

class HomeViewModel internal constructor(app: Application, private val repository: MangaRepository) : BaseViewModel(app) {

    lateinit var mangas: List<Manga>

    init {
        getMangas()
    }

    private fun getMangas() {
        launch {
            mangas = repository.getMangas()
            return@launch mangas.isNotEmpty()
        }
    }

}
