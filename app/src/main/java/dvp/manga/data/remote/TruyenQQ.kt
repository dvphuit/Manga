package dvp.manga.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dvp.manga.data.model.ChapContent
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import org.jsoup.Jsoup

/**
 * @author dvphu on 16,March,2020
 */

class TruyenQQ : BaseCrawler(){
    private val url = ""
    override fun getMangas(): LiveData<List<Manga>> {
        val mangas = mutableListOf<Manga>()
        val manga = Manga()
        val body = Jsoup.connect(url).get().body()

        manga.name = body.getElementById("title").text()
        manga.cover = body.getElementById("cover").text()
        manga.href = body.getElementById("href").text()
        manga.genres = body.getElementById("genres").text()
        mangas.add(manga)

        return MutableLiveData<List<Manga>>().apply {mangas.toList()}
    }

    override fun getChapters(): LiveData<List<Chapter>> {
        return MutableLiveData<List<Chapter>>().apply { listOf<Chapter>()}
    }

    override fun getChapContent(): LiveData<List<ChapContent>> {
        return MutableLiveData<List<ChapContent>>().apply { listOf<ChapContent>()}
    }

}