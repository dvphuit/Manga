package dvp.manga.data.repository

import dvp.manga.data.local.dao.MangaDao
import dvp.manga.data.remote.BaseCrawler
import dvp.manga.ui.responseLiveData

class MangaRepository(private val crawler: BaseCrawler, private val dao: MangaDao) {

    fun getMangas(page: Int) = responseLiveData(
        dbQuery = { dao.getMangasBySlug("-") },
        netCall = { crawler.getMangas(page) },
        saveNetCall = { list ->
            dao.upsert(list)
            dao.updateSlug("-", list.map { it.id })
        })

//    fun searchManga(query: String, page: Int) = responseLiveData(
//        dbQuery = { dao.getMangasBySlug("search") },
//        netCall = { crawler.searchManga(query, page) },
//        saveNetCall = { list ->
//            dao.upsert(list)
//            dao.updateSlug("searched", list.map { it.id })
//        })

    suspend fun searchManga(query: String, page: Int) = crawler.searchManga(query, page)

    val top = responseLiveData(
        dbQuery = { dao.getTop() },
        netCall = { crawler.getTopManga() },
        saveNetCall = { list ->
            //            dao.upsert(list)
//            dao.updateSlug("favourite", list.map { it.id })
        })

    val favourite = responseLiveData(
        dbQuery = { dao.getMangasBySlug("favourite") },
        netCall = {
            crawler.getMangaFavourite()
        },
        saveNetCall = { list ->
            dao.upsert(list)
            dao.updateSlug("favourite", list.map { it.id })
        })

    val lastUpdated = responseLiveData(
        dbQuery = { dao.getMangasBySlug("last_updated") },
        netCall = { crawler.getMangaLastUpdated() },
        saveNetCall = { list ->
            dao.upsert(list)
            dao.updateSlug("last_updated", list.map { it.id })
        })

    val forBoy = responseLiveData(
        dbQuery = { dao.getMangasBySlug("boy") },
        netCall = { crawler.getMangaForBoy() },
        saveNetCall = { list ->
            dao.upsert(list)
            dao.updateSlug("boy", list.map { it.id })
        })

    val forGirl = responseLiveData(
        dbQuery = { dao.getMangasBySlug("girl") },
        netCall = { crawler.getMangaForGirl() },
        saveNetCall = { list ->
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