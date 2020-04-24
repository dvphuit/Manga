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

    suspend fun getTopManga(): List<Manga> {
        return crawler.getTopManga()
    }

    suspend fun getFavouriteMangas(): List<Manga> {
        return crawler.getMangaFavourite()
    }

    suspend fun getLastUpdatedMangas(): List<Manga> {
        return crawler.getMangaLastUpdated()
    }

    suspend fun getMangaForBoy(): List<Manga> {
        return crawler.getMangaForBoy()
    }

    suspend fun getMangaForGirl(): List<Manga> {
        return crawler.getMangaForGirl()
    }

    companion object {
        @Volatile
        private var instance: MangaRepository? = null

        fun getInstance(crawler: BaseCrawler) =
            instance ?: synchronized(this) {
                instance ?: MangaRepository(crawler).also { instance = it }
            }
    }
}