package dvp.manga.data.remote

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dvp.manga.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * @author dvphu on 16,March,2020
 */

class TruyenQQ(private val ctx: Context) : BaseCrawler() {

    init {
        initPicasso()
    }

    private val url = "http://truyenqq.com"
    override suspend fun getTopManga(): List<Manga> {
        val mangas = mutableListOf<Manga>()
        val list = withContext(Dispatchers.IO) {
            getBody("$url/index.html").getElementsByClass("tile is-child")
        }
        list.map { element ->
            Manga(host = url)
            mangas.add(parseTopManga(element))
        }
        return mangas
    }

    override suspend fun getMangaFavourite(): List<Manga> {
        val body = withContext(Dispatchers.IO) {
            getBody("$url/truyen-yeu-thich.html?country=4").getElementsByClass("story-item")
        }
        return parseManga(body)
    }

    override suspend fun getMangaForBoy(): List<Manga> {
        val body = withContext(Dispatchers.IO) {
            getBody("$url/truyen-con-trai.html?country=4").getElementsByClass("story-item")
        }
        return parseManga(body)
    }

    override suspend fun getMangaForGirl(): List<Manga> {
        val body = withContext(Dispatchers.IO) {
            getBody("$url/truyen-con-gai.html?country=4").getElementsByClass("story-item")
        }
        return parseManga(body)
    }

    override suspend fun getMangaLastUpdated(): List<Manga> {
        val body = withContext(Dispatchers.IO) {
            getBody("$url/truyen-moi-cap-nhat.html?country=4").getElementsByClass("story-item")
        }
        return parseManga(body)
    }


    override suspend fun getMangas(page: Int): List<Manga> {
        val body = withContext(Dispatchers.IO) {
            getBody("$url/truyen-con-trai/trang-$page.html?country=4").getElementsByClass("story-item")
        }
        return parseManga(body)
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

    override suspend fun searchManga(query: String, page: Int): List<Manga> {
        val body = withContext(Dispatchers.IO) {
            getBody("$url/tim-kiem/trang-$page?q=$query").getElementsByClass("story-item")
        }
        return parseManga(body)
    }

    private fun parseManga(body: Elements): List<Manga> {
        val mangas = mutableListOf<Manga>()
        body.map { element ->
            val manga = Manga()
            with(element) {
                manga.name = getElementsByClass("title-book").text()
                manga.href = getElementsByClass("title-book").select("a").attr("href")
                manga.last_chap = getElementsByClass("episode-book").text()
                manga.cover = getElementsByClass("story-cover").attr("src")
                manga.info = parseInfo(element)
                manga.genres = parseGenres(element)
                mangas.add(manga)
            }
        }
        return mangas
    }

    private fun parseTopManga(element: Element): Manga {
        val manga = Manga()
        with(element.selectFirst("a")) {
            manga.name = selectFirst("h3").text()
            manga.cover = selectFirst("img.cover").attr("src")
            manga.href = attr("href")
            manga.last_chap = selectFirst("div.chapter").text()
        }
        return manga
    }

    private fun parseInfo(element: Element): Info {
        val info = Info()
        element.getElementsByClass("info").forEach {
            val value = it.text().replace(",", "")
            if (value.contains("tình trạng", true)) info.status = value
            if (value.contains("lượt xem", true)) info.viewed = value
            if (value.contains("theo dõi", true)) info.followed = Regex("\\d+").find(value)?.value ?: "0"
        }
        info.description = element.getElementsByClass("excerpt").first().text()
        return info
    }

    private fun parseGenres(element: Element): List<Genres> {
        val genres = mutableListOf<Genres>()
        element.getElementsByClass("list-tags").select("a").forEach {
            genres.add(Genres(it.text(), it.attr("href")))
        }
        return genres
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