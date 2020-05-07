package dvp.manga.data.remote

import android.util.Log
import dvp.manga.data.model.ChapContent
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import dvp.manga.ui.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * @author dvphu on 10,March,2020
 */

abstract class BaseCrawler {
    abstract suspend fun getTopManga(): ResultData<List<Manga>>
    abstract suspend fun getMangaLastUpdated(): ResultData<List<Manga>>
    abstract suspend fun getMangaFavourite(): ResultData<List<Manga>>
    abstract suspend fun getMangaForBoy(): ResultData<List<Manga>>
    abstract suspend fun getMangaForGirl(): ResultData<List<Manga>>
    abstract suspend fun getMangas(page: Int): ResultData<List<Manga>>
    abstract suspend fun getChapters(href: String): ResultData<List<Chapter>>
    abstract suspend fun getChapContent(href: String): ResultData<List<ChapContent>>
    abstract suspend fun searchManga(query: String, page: Int): ResultData<List<Manga>>

    private suspend fun getBody(url: String): ResultData<Element> {
        return try {
            withContext(Dispatchers.IO) {
                ResultData.success(Jsoup.connect(url).timeout(5000).get().body())
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.localizedMessage!!)
            ResultData.failure(e.localizedMessage!!)
        }
    }

    protected suspend fun <T> parseData(url: String, selector: String, parser: (Elements) -> T): ResultData<T> {
        return when (val result = getBody(url)) {
            is ResultData.Success -> {
                val elements = result.value.getElementsByClass(selector)
                ResultData.success(parser(elements))
            }
            else -> {
                ResultData.failure(result.toString())
            }
        }
    }
}

