package dvp.app.azmanga.data.remote

import dvp.app.azmanga.data.entities.Genre
import dvp.app.azmanga.data.entities.Manga
import dvp.app.azmanga.data.entities.SectionRoute
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class TruyenQQ(baseUrl: String = "http://truyenqq.com") : Scrapper(baseUrl) {

    init {
        routes[SectionRoute.LAST_UPDATE] = "truyen-moi-cap-nhat"
        routes[SectionRoute.FAVOURITE] = "truyen-yeu-thich"
        routes[SectionRoute.FOR_BOY] = "truyen-con-trai"
        routes[SectionRoute.FOR_GIRL] = "truyen-con-gai"
        routes[SectionRoute.NEW_MANGA] = "truyen-tranh-moi"
        routes[SectionRoute.ACTION] = "the-loai/action-26"
        routes[SectionRoute.ADVENTURE] = "the-loai/adventure-27"
        routes[SectionRoute.COMEDY] = "the-loai/comedy-28"
        routes[SectionRoute.DETECTIVE] = "the-loai/detective-100"
        routes[SectionRoute.DRAMA] = "the-loai/drama-29"
        routes[SectionRoute.FANTASY] = "the-loai/fantasy-30"
        routes[SectionRoute.ISEKAI] = "the-loai/isekai-85"
        routes[SectionRoute.MAGIC] = "the-loai/magic-58"
        routes[SectionRoute.PSYCHOLOGICAL] = "the-loai/psychological-40"
        routes[SectionRoute.SHOUNEN] = "the-loai/shounen-31"
        routes[SectionRoute.SPORT] = "the-loai/sports-57"
        routes[SectionRoute.SUPER_NATURAL] = "the-loai/supernatural-32"
        routes[SectionRoute.WEBTOON] = "the-loai/webtoon-55"
    }

    private fun buildPath(name: SectionRoute?, page: Int = 1, country: String = "country=4") =
        "${routes[name]}/trang-$page?$country"

    override suspend fun getMangas(section: SectionRoute, page: Int): List<Manga> {
        return parseData(
            path = buildPath(section, page),
            selector = "story-item",
            parser = { parseManga(it) }
        )
    }

    override suspend fun getTopManga(): List<Manga> {
        return parseData(
            path = "index",
            selector = "tile is-child",
            parser = { parseTopManga(it) }
        )
    }

//    override suspend fun getChapters(mangaId: Int, href: String): Resource<List<Chapter>> {
//        return parseData(
//            url = href,
//            selector = "works-chapter-item",
//            parser = {
//                it.map { element ->
//                    with(element.select("div")) {
//                        Chapter(
//                            manga_id = mangaId,
//                            index = this[1].text().numberF,
//                            name = this[1].text(),
//                            href = this[1].select("a").attr("href"),
//                            upload_date = this[2].text()
//                        )
//                    }
//                }
//            }
//        )
//    }
//
//    override suspend fun getChapContent(href: String): Resource<List<ChapContent>> {
//        return parseData(
//            url = href,
//            selector = "lazy",
//            parser = {
//                it.map { element ->
//                    with(element) {
//                        ChapContent(img_url = attr("src"))
//                    }
//                }
//            }
//        )
//    }
//
//    override suspend fun searchManga(query: String, page: Int): Resource<List<Manga>> {
//        return parseData(
//            url = "$url/tim-kiem/trang-$page?q=$query",
//            selector = "story-item",
//            parser = { parseManga(it) }
//        )
//    }

    private fun parseManga(body: Elements): List<Manga> {
        return body.map { element ->
            with(element) {
                Manga(
                    name = getElementsByClass("title-book").text(),
                    href = getElementsByClass("title-book").select("a").attr("href"),
                    last_chap = getElementsByClass("episode-book").text(),
                    cover = getElementsByClass("story-cover").attr("src"),
                    description = element.getElementsByClass("excerpt").first()?.text(),
                    genres = parseGenres(element)
                ).apply {
                    element.getElementsByClass("info").forEach {
                        val value = it.text().replace(",", "")
                        if (value.contains("tình trạng", true)) status = value
                        if (value.contains("lượt xem", true)) viewed = value
                        if (value.contains("theo dõi", true)) followed =
                            Regex("\\d+").find(value)?.value ?: "0"
                    }
                }

            }
        }
    }

    private fun parseTopManga(list: Elements): List<Manga> {
        return list.map { element ->
            element.selectFirst("a")?.run {
                Manga(
                    name = selectFirst("h3")?.text()!!,
                    cover = selectFirst("img.cover")?.attr("src"),
                    href = attr("href"),
                    last_chap = selectFirst("div.chapter")?.text()
                )
            }!!
        }
    }

    private fun parseGenres(element: Element): List<Genre> {
        val genres = mutableListOf<Genre>()
        element.getElementsByClass("list-tags").select("a").forEach {
            genres.add(Genre(it.text(), it.attr("href")))
        }
        return genres
    }

}