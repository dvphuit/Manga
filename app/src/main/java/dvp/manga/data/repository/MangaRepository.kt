package dvp.manga.data.repository

import dvp.manga.data.local.dao.MangaDao
import dvp.manga.data.model.SectionRoute
import dvp.manga.data.remote.BaseCrawler
import dvp.manga.ui.fetchData
import dvp.manga.ui.responseLiveData

class MangaRepository(private val crawler: BaseCrawler, private val dao: MangaDao) {

    fun getMangas(page: Int) = responseLiveData(
        dbQuery = { dao.getMangasBySlug("-") },
        netCall = { crawler.getMangas(page) },
        saveNetCall = { list ->
            dao.upsert(list)
            dao.updateSlug("-", list.map { it.id })
        })

    fun getMangas(section: SectionRoute, page: Int = 1) = responseLiveData(
        dbQuery = { dao.getMangasBySlug(section.name) },
        netCall = { crawler.getMangas(section, page) },
        saveNetCall = { list ->
            dao.upsert(list)
            dao.updateSlug(section.name, list.map { it.id })
        })

    suspend fun fetchMangas(section: SectionRoute, page: Int = 1) =
        fetchData(
            netCall = { crawler.getMangas(section, page) },
            saveNetCall = { list ->
                dao.upsert(list)
                dao.updateSlug(section.name, list.map { it.id })
            })

    suspend fun searchManga(query: String, page: Int) =
        fetchData(
            netCall = { crawler.searchManga(query, page) },
            saveNetCall = { list ->
//                dao.upsert(list)
//                dao.updateSlug(section.name, list.map { it.id })
            })

    val top = responseLiveData(
        dbQuery = { dao.getTop() },
        netCall = { crawler.getTopManga() },
        saveNetCall = { list ->
            //            dao.upsert(list)
//            dao.updateSlug("favourite", list.map { it.id })
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