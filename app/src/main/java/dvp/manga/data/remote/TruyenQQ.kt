package dvp.manga.data.remote

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dvp.manga.data.model.ChapContent
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

/**
 * @author dvphu on 16,March,2020
 */

class TruyenQQ(private val ctx: Context) : BaseCrawler() {

    init {
        initPicasso()
    }

    private val url = "http://truyenqq.com"
    override suspend fun getMangas(page: Int): List<Manga> {
        val mangas = mutableListOf<Manga>()
        val list = withContext(Dispatchers.IO) {
            getBody("$url/truyen-con-trai/trang-$page.html?country=4").getElementsByClass("story-item")
        }
        list.map { element ->
            val manga = Manga(host = url)
            with(element) {
                manga.name = getElementsByClass("title-book").text()
                manga.href = getElementsByClass("title-book").select("a").attr("href")
                manga.last_chap = getElementsByClass("episode-book").text()
                manga.cover = getElementsByClass("story-cover").attr("src")
                mangas.add(manga)
            }
        }
        return mangas
    }

    override suspend fun getChapters(href: String): List<Chapter> {
        val chaps = mutableListOf<Chapter>()
        val list = withContext(Dispatchers.IO) {
            getBody(href).getElementsByClass("works-chapter-item")
        }
        list.map { element ->
            val chap = Chapter()
            with(element) {
                chap.name = select("div > a").text()
                chap.href = select("div > a").attr("href")
                chaps.add(chap)
            }
        }
        return chaps
    }

    override suspend fun getChapContent(href: String): List<ChapContent> {
        val contents = mutableListOf<ChapContent>()
        val list = withContext(Dispatchers.IO) {
            getBody(href).getElementsByClass("lazy")
        }
        list.map { element ->
            val content = ChapContent()
            with(element) {
                content.img_url = attr("src")
                contents.add(content)
            }
        }
        return contents
    }

    override suspend fun searchManga(query: String): List<Manga> {
        val mangas = mutableListOf<Manga>()
        val list = withContext(Dispatchers.IO) {
            getBody("$url/tim-kiem.html?q=$query").getElementsByClass("story-item")
        }
        list.map { element ->
            val manga = Manga(host = url)
            with(element) {
                manga.name = getElementsByClass("title-book").text()
                manga.href = getElementsByClass("title-book").select("a").attr("href")
                manga.last_chap = getElementsByClass("episode-book").text()
                manga.cover = getElementsByClass("story-cover").attr("src")
                mangas.add(manga)
            }
        }
        return mangas
    }

    //region init TruyenQQ
    private fun initPicasso() {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .header("Referer", url)
                .build()
            chain.proceed(request)
        }.build()
        val okHttpDownloader = OkHttp3Downloader(client)
        val builder = Picasso.Builder(ctx)
        builder.downloader(okHttpDownloader)
        Picasso.setSingletonInstance(builder.build())
    }

    companion object {
        @Volatile
        private var instance: TruyenQQ? = null

        fun getInstance(ctx: Context) = instance ?: synchronized(this) {
            instance ?: TruyenQQ(ctx).also { instance = it }
        }
    }

    //endregion
}