package dvp.manga.data.remote

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dvp.manga.data.model.ChapContent
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup

/**
 * @author dvphu on 16,March,2020
 */

class TruyenQQ(private val ctx: Context) : BaseCrawler() {

    init {
        initPicasso()
    }

    private val url = "http://truyenqq.com/"
    override suspend fun getMangas(): List<Manga> {
        val mangas = mutableListOf<Manga>()
        val list = withContext(Dispatchers.IO) {
            Jsoup.connect(url).get().body().getElementsByClass("story-item")
        }
        list.map {
            val manga = Manga(host = url)
            with(it){
                manga.name = getElementsByClass("title-book").text()
                manga.href = getElementsByClass("title-book").select("a").attr("href")
                manga.last_chap = getElementsByClass("episode-book").text()
                manga.cover = getElementsByClass("story-cover").attr("src")
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

    private fun initPicasso() {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            var request: Request = chain.request()
            val requestBuilder: Request.Builder = request.newBuilder()
                .header("Referer", url)
            request = requestBuilder.build()
            chain.proceed(request)
        }.build()
        val okHttpDownloader = OkHttp3Downloader(client)
        val builder = Picasso.Builder(ctx)
        builder.downloader(okHttpDownloader)
        Picasso.setSingletonInstance(builder.build())
    }

    companion object {
        private var network: TruyenQQ? = null
        fun getInstance(ctx: Context): TruyenQQ {
            if (network == null) {
                synchronized(TruyenQQ::class.java) {
                    if (network == null) {
                        network = TruyenQQ(ctx)
                    }
                }
            }
            return network!!
        }
    }
}