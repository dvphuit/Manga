package dvp.manga.ui.detail

import androidx.lifecycle.ViewModel
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.ChapterRepository

class MangaDetailVM(repository: ChapterRepository, _manga: Manga) : ViewModel() {
    val manga = _manga
    val chapters = repository.getChaps(_manga.id, _manga.href!!)
}
