package dvp.manga.ui.detail

import android.app.Application
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.ChapterRepository
import dvp.manga.ui.BaseViewModel

class MangaDetailVM(app: Application, private val repository: ChapterRepository, private val _manga: Manga) : BaseViewModel(app) {
    lateinit var chaps: List<Chapter>
    var manga = _manga

    init {
        getChapters()
    }

    private fun getChapters() {
        launch {
            chaps = repository.getChaps(_manga.href!!)
            return@launch chaps.isNotEmpty()
        }
    }
}
