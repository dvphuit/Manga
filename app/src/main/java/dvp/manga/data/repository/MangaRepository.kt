package dvp.manga.data.repository

import dvp.manga.data.local.dao.MangaDao
import dvp.manga.data.model.Manga
import dvp.manga.data.remote.BaseCrawler
import dvp.manga.ui.responseLiveData

class MangaRepository(private val crawler: BaseCrawler, private val dao: MangaDao) {

    suspend fun getMangas(page: Int): List<Manga> {
        val mangas = crawler.getMangas(page)
        dao.upsert(mangas)
        return mangas
    }

    suspend fun searchManga(query: String, page: Int): List<Manga> {
        return crawler.searchManga(query, page)
    }

    suspend fun getTopManga(): List<Manga> {
        return crawler.getTopManga()
    }

    val favourite = responseLiveData(
        roomQueryToRetrieveData = { dao.getMangasBySlug("favourite") },
        networkRequest = { crawler.getMangaFavourite() },
        roomQueryToSaveData = { list ->
            dao.upsert(list)
            dao.updateSlug("favourite", list.map { it.id })
        })

    val lastUpdated = responseLiveData(
        roomQueryToRetrieveData = { dao.getMangasBySlug("last_updated") },
        networkRequest = { crawler.getMangaLastUpdated() },
        roomQueryToSaveData = { list ->
            dao.upsert(list)
            dao.updateSlug("last_updated", list.map { it.id })
        })

    val forBoy = responseLiveData(
        roomQueryToRetrieveData = { dao.getMangasBySlug("boy") },
        networkRequest = { crawler.getMangaForBoy() },
        roomQueryToSaveData = { list ->
            dao.upsert(list)
            dao.updateSlug("boy", list.map { it.id })
        })

    val forGirl = responseLiveData(
        roomQueryToRetrieveData = { dao.getMangasBySlug("girl") },
        networkRequest = { crawler.getMangaForGirl() },
        roomQueryToSaveData = { list ->
            dao.upsert(list)
            dao.updateSlug("girl", list.map { it.id })
        })

    companion object {
        @Volatile
        private var instance: MangaRepository? = null

        fun getInstance(crawler: BaseCrawler, dao: MangaDao) =
            instance ?: synchronized(this) {
                instance ?: MangaRepository(crawler, dao).also { instance = it }
            }
    }
}