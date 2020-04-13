package dvp.manga.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.ChapterRepository
import kotlinx.coroutines.Dispatchers

class MangaDetailVM(private val repository: ChapterRepository, private val _manga: Manga) : ViewModel() {
    var manga = _manga

    val chapters = liveData(Dispatchers.IO) {
        val commentResponse = repository.getChaps(_manga.href!!)
        emit(commentResponse)
    }
}
