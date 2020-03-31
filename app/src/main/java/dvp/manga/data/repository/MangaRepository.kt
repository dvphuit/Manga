package dvp.manga.data.repository

import dvp.manga.data.model.Manga
import dvp.manga.data.remote.BaseCrawler

class MangaRepository(private val crawler: BaseCrawler) {

    private lateinit var mangas: List<Manga>

    suspend fun getMangas(page: Int): List<Manga> {
        mangas = crawler.getMangas(page)
        return mangas
    }

    suspend fun searchManga(query: String, page: Int): List<Manga> {
        mangas = crawler.searchManga(query, page)
        return mangas
    }

    companion object {
        @Volatile private var instance: MangaRepository? = null
        fun getInstance(crawler: BaseCrawler) =
            instance ?: synchronized(this) {
                instance ?: MangaRepository(crawler).also { instance = it }
            }
    }
}