package dvp.manga.ui.detail

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvp.manga.data.model.Manga
import dvp.manga.data.model.MangaInfo
import dvp.manga.data.model.MetaData
import dvp.manga.data.repository.ChapterRepository
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.ResultData
import dvp.manga.ui.adapter.PagedChap
import kotlinx.coroutines.launch

class MangaDetailVM(private val mangaRepo: MangaRepository, chapRepo: ChapterRepository, _manga: Manga) : ViewModel() {

    private val chapPerPage = 50

    val manga = mangaRepo.getMangaInfo(_manga.id)
    private val chapters = chapRepo.getChaps(_manga.id, _manga.href!!)

    val pageChaps = Transformations.map(chapters) {
        when (it) {
            is ResultData.Success -> {
                val grouped = it.value
                    .groupBy { chap -> (chap.index / (chapPerPage + .1)).toInt() }
                    .map { (_, chaps) ->
                        val first = chaps.first().index.toInt()
                        val last = chaps.last().index.toInt()
                        PagedChap("$first - $last", chaps)
                    }
                return@map ResultData.success(grouped)
            }
            is ResultData.Failure -> {
                return@map ResultData.failure<List<PagedChap>>(it.message)
            }
            is ResultData.Loading -> {
                return@map ResultData.loading(null)
            }
        }
    }


    fun setBookmark(mangaInfo: MangaInfo) {
        viewModelScope.launch {
            val state = mangaInfo.metaData?.bookmarked ?: false
//            mangaInfo.metaData?.bookmarked = !state
            val metaData = MetaData(
                manga_id = mangaInfo.manga!!.id,
                bookmarked = !state
            )
            mangaRepo.saveMetaData(metaData)
        }
    }

}
