package dvp.manga.data.remote

import android.content.Context
import dvp.manga.data.model.ChapContent
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Genre
import dvp.manga.data.model.Manga
import dvp.manga.ui.ResultData
import dvp.manga.utils.hash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * @author dvphu on 16,March,2020
 */

class TruyenQQ(private val ctx: Context) : BaseCrawler() {

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

    override suspend fun getMangaFavourite(): ResultData<List<Manga>> {
        val body = getBody("$url/truyen-yeu-thich.html?country=4").getElementsByClass("story-item")
        val ret = parseManga(body)
        if (ret.isEmpty()) formatError<List<Manga>>("list empty")
        return ResultData.success(ret)
    }

    override suspend fun getMangaForBoy(): ResultData<List<Manga>> {
        val body = getBody("$url/truyen-con-trai.html?country=4").getElementsByClass("story-item")
        val ret = parseManga(body)
        if (ret.isEmpty()) formatError<List<Manga>>("list empty")
        return ResultData.success(ret)
    }

    override suspend fun getMangaForGirl(): ResultData<List<Manga>> {
        val body = getBody("$url/truyen-con-gai.html?country=4").getElementsByClass("story-item")
        val ret = parseManga(body)
        if (ret.isEmpty()) formatError<List<Manga>>("list empty")
        return ResultData.success(ret)
    }

    override suspend fun getMangaLastUpdated(): ResultData<List<Manga>> {
        val body = getBody("$url/truyen-moi-cap-nhat.html?country=4").getElementsByClass("story-item")
        val ret = parseManga(body)
        if (ret.isEmpty()) formatError<List<Manga>>("list empty")
        return ResultData.success(ret)
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
                element.getElementsByClass("info").forEach {
                    val value = it.text().replace(",", "")
                    if (value.contains("tình trạng", true)) manga.status = value
                    if (value.contains("lượt xem", true)) manga.viewed = value
                    if (value.contains("theo dõi", true)) manga.followed = Regex("\\d+").find(value)?.value ?: "0"
                }
                manga.description = element.getElementsByClass("excerpt").first().text()
                manga.genres = parseGenres(element)
                manga.id = manga.name.hash
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
            manga.id = manga.name.hash
        }
        return manga
    }

    private fun parseGenres(element: Element): List<Genre> {
        val genres = mutableListOf<Genre>()
        element.getElementsByClass("list-tags").select("a").forEach {
            genres.add(Genre(it.text(), it.attr("href")))
        }
        return genres
    }

    //region init TruyenQQ
//    private fun initPicasso() {
//        val client = OkHttpClient.Builder().addInterceptor { chain ->
//            val request = chain.request()
//                .newBuilder()
//                .header("Referer", url)
//                .build()
//            chain.proceed(request)
//        }.build()
//        val okHttpDownloader = OkHttp3Downloader(client)
//        val builder = Picasso.Builder(ctx)
//        builder.downloader(okHttpDownloader)
//        Picasso.setSingletonInstance(builder.build())
//    }

    companion object {
        @Volatile
        private var instance: TruyenQQ? = null

        fun getInstance(ctx: Context) = instance ?: synchronized(this) {
            instance ?: TruyenQQ(ctx).also { instance = it }
        }
    }

    //endregion
}