package dvp.manga.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dvp.manga.data.local.DaoManager
import dvp.manga.data.local.dao.MangaDao
import dvp.manga.data.local.dao.MetaDataDao
import dvp.manga.data.model.MangaInfo
import dvp.manga.data.model.MetaData
import dvp.manga.data.model.SectionRoute
import dvp.manga.data.remote.BaseCrawler
import dvp.manga.ui.fetchData
import dvp.manga.ui.responseLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MangaRepository(private val crawler: BaseCrawler, private val dao: MangaDao, private val metaDao: MetaDataDao) {

    fun getMangaInfo(mangaId: Int): LiveData<MangaInfo> = liveData(Dispatchers.IO) {
        emitSource(dao.getMangaInf(mangaId))
    }

    suspend fun saveMetaData(metaData: MetaData) {
        withContext(Dispatchers.IO) {
            metaDao.upsert(metaData)
        }
    }

    fun getMangas(section: SectionRoute, page: Int = 1) = responseLiveData(
        dbQuery = { dao.getMangasBySlug(section.name) },
        netCall = { crawler.getMangas(section, page) },
        saveNetCall = { list ->
            dao.upsert(list)
            DaoManager.getInstance().addQueue {
                metaDao.upsertMetaSlug(list, section.name)
            }
        })

    suspend fun fetchMangas(section: SectionRoute, page: Int = 1) =
        fetchData(
            netCall = { crawler.getMangas(section, page) },
            saveNetCall = { list ->
                dao.upsert(list)

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

        fun getInstance(crawler: BaseCrawler, dao: MangaDao, metaDao: MetaDataDao) =
            instance ?: synchronized(this) {
                instance ?: MangaRepository(crawler, dao, metaDao).also { instance = it }
            }
    }
}
