package dvp.app.azmanga.data.remote


import dvp.app.azmanga.data.entities.Manga
import dvp.app.azmanga.data.entities.SectionRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.select.Elements


abstract class Scrapper(private val baseUrl: String) {
    private val job = Job()
    private val ioScope = Dispatchers.IO + job
    private var cookie: Pair<String, String>? = null

    protected val routes = mutableMapOf<SectionRoute, String>()

    abstract suspend fun getTopManga(): List<Manga>

    abstract suspend fun getMangas(section: SectionRoute, page: Int): List<Manga>

//    abstract suspend fun getChapters(mangaId: Int, href: String): Resource<List<Chapter>>
//    abstract suspend fun getChapContent(href: String): Resource<List<ChapContent>>
//    abstract suspend fun searchManga(query: String, page: Int): Resource<List<Manga>>

    private fun getBody(path: String) = runBlocking(ioScope) {
        connection("$baseUrl/$path").get().body()
    }

    private fun connection(url: String): Connection = runBlocking(ioScope) {
        if (cookie == null) {
            val data = Jsoup.connect(url).get().data()
            val (key, value) = Regex("VinaHost.*(?=\"\\+)").find(data)?.run {
                this.value.split("=")
            }!!
            cookie = key to value
            return@runBlocking connection(url)
        }
        val (key, value) = cookie!!
        return@runBlocking Jsoup.connect(url).cookie(key, value)
    }

    protected fun <T> parseData(
        path: String,
        selector: String,
        parser: (Elements) -> T
    ): T {
        val elements = getBody(path).getElementsByClass(selector)
        return parser(elements)
    }
}