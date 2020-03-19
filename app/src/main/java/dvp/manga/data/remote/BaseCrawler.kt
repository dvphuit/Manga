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
    abstract suspend fun getMangas(): List<Manga>
    abstract suspend fun getChapters(href: String): List<Chapter>
    abstract suspend fun getChapContent(): List<ChapContent>

    internal fun getBody(url: String): Element {
        return Jsoup.connect(url).timeout(5000).get().body()
    }
}

