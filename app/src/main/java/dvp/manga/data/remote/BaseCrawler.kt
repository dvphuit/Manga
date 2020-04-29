package dvp.manga.data.remote

import dvp.manga.data.model.ChapContent
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import dvp.manga.ui.ResultData
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
 * @author dvphu on 10,March,2020
 */

abstract class BaseCrawler {
    abstract suspend fun getTopManga(): List<Manga>
    abstract suspend fun getMangaLastUpdated(): ResultData<List<Manga>>
    abstract suspend fun getMangaFavourite(): ResultData<List<Manga>>
    abstract suspend fun getMangaForBoy(): ResultData<List<Manga>>
    abstract suspend fun getMangaForGirl(): ResultData<List<Manga>>


    abstract suspend fun getMangas(page: Int): List<Manga>
    abstract suspend fun getChapters(href: String): List<Chapter>
    abstract suspend fun getChapContent(href: String): List<ChapContent>

    abstract suspend fun searchManga(query: String, page: Int): List<Manga>

    internal fun getBody(url: String): Element {
        return Jsoup.connect(url).timeout(5000).get().body()
    }


//    protected suspend fun <T> getData(call: suspend () -> LiveData<T>): ResultData<T> {
//        try {
//            val response = call()
//            if (response.isSuccessful) {
//                val body = response.body()
//                if (body != null) return ResultData.success(body)
//            }
//            return formatError(" ${response.code()} ${response.message()}")
//        } catch (exception: Exception) {
//            return formatError(exception.message!!)
//        }
//    }

    protected fun <T> formatError(errorMessage: String): ResultData<T> {
        return ResultData.failure("Network call has failed for a following reason: $errorMessage")
    }
}

