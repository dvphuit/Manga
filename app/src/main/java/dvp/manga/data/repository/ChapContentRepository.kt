package dvp.manga.data.repository

import dvp.manga.data.local.dao.ChapContentDao
import dvp.manga.data.remote.BaseCrawler
import dvp.manga.ui.responseLiveData

/**
 * @author dvphu on 20,March,2020
 */

class ChapContentRepository(private val crawler: BaseCrawler, private val dao: ChapContentDao) {

    fun getChapContents(chapId: Int, href: String) = responseLiveData(
        dbQuery = { dao.getPages(chapId) },
        netCall = { crawler.getChapContent(href) },
        saveNetCall = { list ->
            dao.upsert(list)
        })


    companion object {
        @Volatile
        private var instance: ChapContentRepository? = null

        fun getInstance(crawler: BaseCrawler, dao: ChapContentDao) = instance ?: synchronized(this) {
            instance ?: ChapContentRepository(crawler, dao).also { instance = it }
        }
    }


}