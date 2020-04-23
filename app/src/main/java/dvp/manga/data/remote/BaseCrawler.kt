package dvp.manga.data.remote

import dvp.manga.data.model.ChapContent
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
 * @author dvphu on 10,March,2020
 */

abstract class BaseCrawler {
    abstract suspend fun getTopManga(): List<Manga>
    abstract suspend fun getMangas(page: Int): List<Manga>
    abstract suspend fun getChapters(href: String): List<Chapter>
    abstract suspend fun getChapContent(href: String): List<ChapContent>

    abstract suspend fun searchManga(query: String, page: Int): List<Manga>

    internal fun getBody(url: String): Element {
        return Jsoup.connect(url).timeout(5000).get().body()
    }
}

