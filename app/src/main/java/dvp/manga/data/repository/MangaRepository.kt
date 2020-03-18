package dvp.manga.data.repository

import dvp.manga.data.model.Manga
import dvp.manga.data.remote.BaseCrawler

class MangaRepository(private val crawler: BaseCrawler) {

    private lateinit var mangas: List<Manga>

    suspend fun getMangas(): List<Manga> {
        mangas = crawler.getMangas()
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