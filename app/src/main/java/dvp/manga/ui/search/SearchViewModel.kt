package dvp.manga.ui.search

import android.app.Application
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.BaseViewModel

class SearchViewModel internal constructor(app: Application, private val repository: MangaRepository) : BaseViewModel(app) {

    var mangas = mutableListOf<Manga>()
    lateinit var top: List<Manga>

    fun searchManga(query: String) {
        launch {
            mangas.addAll(repository.searchManga(query))
            return@launch mangas.isNotEmpty()
        }
    }
}
