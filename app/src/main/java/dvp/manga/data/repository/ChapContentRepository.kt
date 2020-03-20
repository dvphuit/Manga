package dvp.manga.data.repository

import dvp.manga.data.model.ChapContent
import dvp.manga.data.remote.BaseCrawler

/**
 * @author dvphu on 20,March,2020
 */

class ChapContentRepository(private val crawler: BaseCrawler) {

    private lateinit var contents: List<ChapContent>

    suspend fun getContents(href: String): List<ChapContent> {
        contents = crawler.getChapContent(href)
        return contents
    }


    companion object {
        @Volatile
        private var instance: ChapContentRepository? = null

        fun getInstance(crawler: BaseCrawler) = instance ?: synchronized(this) {
            instance ?: ChapContentRepository(crawler).also { instance = it }
        }
    }


}