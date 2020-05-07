package dvp.manga.data.remote

import android.content.Context
import dvp.manga.data.model.ChapContent
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Genre
import dvp.manga.data.model.Manga
import dvp.manga.ui.ResultData
import dvp.manga.utils.hash
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * @author dvphu on 16,March,2020
 */

class TruyenQQ(private val ctx: Context) : BaseCrawler() {

    private val url = "http://truyenqq.com"
    override suspend fun getTopManga(): ResultData<List<Manga>> {
        return parseData(
            url = "$url/index.html",
            selector = "tile is-child",
            parser = { parseTopManga(it) }
        )
    }

    override suspend fun getMangaFavourite(): ResultData<List<Manga>> {
        return parseData(
            url = "$url/truyen-yeu-thich.html?country=4",
            selector = "story-item",
            parser = { parseManga(it) }
        )
    }

    override suspend fun getMangaForBoy(): ResultData<List<Manga>> {
        return parseData(
            url = "$url/truyen-con-trai.html?country=4",
            selector = "story-item",
            parser = { parseManga(it) }
        )
    }

    override suspend fun getMangaForGirl(): ResultData<List<Manga>> {
        return parseData(
            url = "$url/truyen-con-gai.html?country=4",
            selector = "story-item",
            parser = { parseManga(it) }
        )
    }

    override suspend fun getMangaLastUpdated(): ResultData<List<Manga>> {
        return parseData(
            url = "$url/truyen-moi-cap-nhat.html?country=4",
            selector = "story-item",
            parser = { parseManga(it) }
        )
    }


    override suspend fun getMangas(page: Int): ResultData<List<Manga>> {
        return parseData(
            url = "$url/truyen-con-trai/trang-$page.html?country=4",
            selector = "story-item",
            parser = { parseManga(it) }
        )
    }

    override suspend fun getChapters(href: String): ResultData<List<Chapter>> {
        return parseData(
            url = href,
            selector = "works-chapter-item",
            parser = {
                val chaps = mutableListOf<Chapter>()
                it.map { element ->
                    val chap = Chapter()
                    with(element) {
                        chap.name = select("div > a").text()
                        chap.href = select("div > a").attr("href")
                        chaps.add(chap)
                    }
                }
                return@parseData chaps
            }
        )
    }

    override suspend fun getChapContent(href: String): ResultData<List<ChapContent>> {
        return parseData(
            url = href,
            selector = "lazy",
            parser = {
                val contents = mutableListOf<ChapContent>()
                it.map { element ->
                    val content = ChapContent()
                    with(element) {
                        content.img_url = attr("src")
                        contents.add(content)
                    }
                }
                return@parseData contents
            }
        )
    }

    override suspend fun searchManga(query: String, page: Int): ResultData<List<Manga>> {
        return parseData(
            url = "$url/tim-kiem/trang-$page?q=$query",
            selector = "story-item",
            parser = { parseManga(it) }
        )
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

    private fun parseTopManga(list: Elements): List<Manga> {
        return list.map { element ->
            with(element.selectFirst("a")) {
                Manga(
                    name = selectFirst("h3").text(),
                    cover = selectFirst("img.cover").attr("src"),
                    href = attr("href"),
                    last_chap = selectFirst("div.chapter").text()
                )
            }
        }
    }

    private fun parseGenres(element: Element): List<Genre> {
        val genres = mutableListOf<Genre>()
        element.getElementsByClass("list-tags").select("a").forEach {
            genres.add(Genre(it.text(), it.attr("href")))
        }
        return genres
    }

    //region init TruyenQQ
    companion object {
        @Volatile
        private var instance: TruyenQQ? = null

        fun getInstance(ctx: Context) = instance ?: synchronized(this) {
            instance ?: TruyenQQ(ctx).also { instance = it }
        }
    }

    //endregion
}