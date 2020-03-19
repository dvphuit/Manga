package dvp.manga.data.repository

import dvp.manga.data.model.Chapter
import dvp.manga.data.remote.BaseCrawler

/**
 * @author dvphu on 19,March,2020
 */

class ChapterRepository(private val crawler: BaseCrawler) {

    private lateinit var chaps: List<Chapter>

    suspend fun getChaps(href: String): List<Chapter> {
        chaps = crawler.getChapters(href)
        return chaps
    }

    companion object {
        @Volatile
        var instance: ChapterRepository? = null

        fun getInstance(crawler: BaseCrawler) = instance ?: synchronized(this) {
            instance ?: ChapterRepository(crawler).also { instance = it }
        }
    }
}