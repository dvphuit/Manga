package dvp.manga.data.repository

import dvp.manga.data.local.dao.ChapterDao
import dvp.manga.data.remote.BaseCrawler
import dvp.manga.ui.responseLiveData

/**
 * @author dvphu on 19,March,2020
 */

class ChapterRepository(private val crawler: BaseCrawler, private val dao: ChapterDao) {

    fun getChaps(mangaId: Int, href: String) = responseLiveData(
        dbQuery = { dao.getChapters(mangaId) },
        netCall = { crawler.getChapters(mangaId, href) },
        saveNetCall = { list ->
            dao.upsert(list)
        })

    companion object {
        @Volatile
        var instance: ChapterRepository? = null

        fun getInstance(crawler: BaseCrawler, dao: ChapterDao) = instance ?: synchronized(this) {
            instance ?: ChapterRepository(crawler, dao).also { instance = it }
        }
    }
}