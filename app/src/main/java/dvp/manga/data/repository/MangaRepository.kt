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
        private lateinit var instance: MangaRepository
        fun getInstance(crawler: BaseCrawler): MangaRepository {
            if (!::instance.isInitialized) {
                synchronized(MangaRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = MangaRepository(crawler)
                    }
                }
            }
            return instance
        }

    }
}