package dvp.manga.data.remote

import android.content.Context
import dvp.manga.data.model.*
import dvp.manga.ui.ResultData
import dvp.manga.utils.numberF
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * @author dvphu on 16,March,2020
 */

class TruyenQQ(private val ctx: Context) : BaseCrawler() {

    private val url = "http://truyenqq.com"

    val genresMap = mapOf(
        "Action" to "$url/the-loai/action-26.html",
        "Adventure" to "$url/the-loai/adventure-27.html",
        "Comedy" to "$url/the-loai/comedy-28.html",
        "Detective" to "$url/the-loai/detective-100.html",
        "Drama" to "$url/the-loai/drama-29.html",
        "Fantasy" to "$url/the-loai/fantasy-30.html",
        "Isekai" to "$url/the-loai/isekai-85.html",
        "Magic" to "$url/the-loai/magic-58.html",
        "Psychological" to "$url/the-loai/psychological-40.html",
        "Shounen" to "$url/the-loai/shounen-31.html",
        "Sports" to "$url/the-loai/sports-57.html",
        "Supernatural" to "$url/the-loai/supernatural-32.html",
        "Webtoon" to "$url/the-loai/webtoon-55.html"
    )

    init {
        routes[SectionRoute.LAST_UPDATE] = "truyen-moi-cap-nhat"
        routes[SectionRoute.FAVOURITE] = "truyen-yeu-thich"
        routes[SectionRoute.FOR_BOY] = "truyen-con-trai"
        routes[SectionRoute.FOR_GIRL] = "truyen-con-gai"
        routes[SectionRoute.NEW_MANGA] = "truyen-tranh-moi"
    }

    private fun buildUrl(name: SectionRoute?, page: Int = 1, country: String = "country=4") = "$url/${routes[name]}/trang-$page?$country"

    override suspend fun getMangas(section: SectionRoute?, page: Int) : ResultData<List<Manga>>{
        return parseData(
            url = buildUrl(section, page),
            selector = "story-item",
            parser = { parseManga(it) }
        )
    }

    override suspend fun getTopManga(): ResultData<List<Manga>> {
        return parseData(
            url = "$url/index",
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

    override suspend fun getChapters(mangaId: Int, href: String): ResultData<List<Chapter>> {
        return parseData(
            url = href,
            selector = "works-chapter-item",
            parser = {
                it.map { element ->
                    with(element.select("div")) {
                        Chapter(
                            manga_id = mangaId,
                            index = this[1].text().numberF,
                            name = this[1].text(),
                            href = this[1].select("a").attr("href"),
                            upload_date = this[2].text()
                        )
                    }
                }
            }
        )
    }

    override suspend fun getChapContent(href: String): ResultData<List<ChapContent>> {
        return parseData(
            url = href,
            selector = "lazy",
            parser = {
                it.map { element ->
                    with(element) {
                        ChapContent(img_url = attr("src"))
                    }
                }
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
        return body.map { element ->
            with(element) {
                Manga(
                    name = getElementsByClass("title-book").text(),
                    href = getElementsByClass("title-book").select("a").attr("href"),
                    last_chap = getElementsByClass("episode-book").text(),
                    cover = getElementsByClass("story-cover").attr("src"),
                    description = element.getElementsByClass("excerpt").first().text(),
                    genres = parseGenres(element)
                ).apply {
                    element.getElementsByClass("info").forEach {
                        val value = it.text().replace(",", "")
                        if (value.contains("tình trạng", true)) status = value
                        if (value.contains("lượt xem", true)) viewed = value
                        if (value.contains("theo dõi", true)) followed = Regex("\\d+").find(value)?.value ?: "0"
                    }
                }

            }
        }
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