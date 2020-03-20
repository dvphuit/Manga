package dvp.manga.ui.home

import android.app.Application
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.BaseViewModel

class HomeViewModel internal constructor(app: Application, private val repository: MangaRepository) : BaseViewModel(app) {

    var mangas = mutableListOf<Manga>()
    lateinit var top: List<Manga>

    fun fetchMangas(page: Int) {
        launch {
            mangas.addAll(repository.getMangas(page))
            return@launch mangas.isNotEmpty()
        }
    }
}
