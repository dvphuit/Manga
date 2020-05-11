package dvp.manga.ui.detail

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.ChapterRepository
import dvp.manga.ui.ResultData
import dvp.manga.ui.adapter.PageChap
import dvp.manga.utils.number

class MangaDetailVM(val repository: ChapterRepository, _manga: Manga) : ViewModel() {

    private val chapPerPage = 50

    val manga = _manga
    private val chapters = repository.getChaps(_manga.id, _manga.href!!)

    val pageChaps = Transformations.map(chapters) {
        when (it) {
            is ResultData.Success -> {
                val ret = it.value.reversed().chunked(chapPerPage).map { chaps ->
                    val end = chaps.first().name?.number
                    val start = chaps.last().name?.number
                    PageChap("$start - $end", chaps.reversed())
                }
                return@map ResultData.success(ret.reversed())
            }
            is ResultData.Failure -> {
                return@map ResultData.failure<List<PageChap>>(it.message)
            }
            is ResultData.Loading -> {
                return@map ResultData.loading(null)
            }
        }
    }


}
