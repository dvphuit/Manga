package dvp.manga.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dvp.manga.data.model.ChapContent
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
 * @author dvphu on 10,March,2020
 */

abstract class BaseCrawler {
    abstract suspend fun getMangas(): List<Manga>
    abstract suspend fun getChapters(): LiveData<List<Chapter>>
    abstract suspend fun getChapContent(): LiveData<List<ChapContent>>

    suspend fun getBody(url: String): Element {
        var body: Element? = null
        try {
            body = withContext(Dispatchers.IO) {
                Jsoup.connect(url).get().body()
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, "Failed to fetch the document")
        } finally {
            return body!!
        }
    }

}