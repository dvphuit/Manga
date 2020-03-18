package dvp.manga.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dvp.manga.data.model.ChapContent
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

/**
 * @author dvphu on 16,March,2020
 */

class TruyenQQ : BaseCrawler() {
    private val url = "http://truyenqq.com/"
    override suspend fun getMangas(): List<Manga> {
        val list = withContext(Dispatchers.IO) {
            Jsoup.connect(url).get().body().getElementsByClass("story-item")
        }
        val mangas = mutableListOf<Manga>()

        list.forEach {
            val manga = Manga(host = url)
            with(it){
                manga.name = getElementsByClass("title-book").text()
                manga.href = getElementsByClass("title-book").select("a").attr("href")
                manga.last_chap = getElementsByClass("episode-book").text()
                manga.cover = getElementsByClass("story-cover").attr("src")
//                manga.cover = "http://i.mangaqq.com/ebook/190x247/truyen-ngan-doraemon-moi-nhat_1583459733.jpg?r=r8645456"
                mangas.add(manga)
            }
        }
        return mangas
    }



    override suspend fun getChapters(): LiveData<List<Chapter>> {
        return MutableLiveData<List<Chapter>>().apply { listOf<Chapter>() }
    }

    override suspend fun getChapContent(): LiveData<List<ChapContent>> {
        return MutableLiveData<List<ChapContent>>().apply { listOf<ChapContent>() }
    }

    companion object {
        private var network: TruyenQQ? = null
        fun getInstance(): TruyenQQ {
            if (network == null) {
                synchronized(TruyenQQ::class.java) {
                    if (network == null) {
                        network = TruyenQQ()
                    }
                }
            }
            return network!!
        }

    }
}