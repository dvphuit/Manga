package dvp.app.azmanga.data.repositories.manga

import androidx.room.withTransaction
import dvp.app.azmanga.base.networkBoundResource
import dvp.app.azmanga.data.database.AppDatabase
import dvp.app.azmanga.data.database.dao.MangaDao
import dvp.app.azmanga.data.entities.SectionRoute
import dvp.app.azmanga.data.remote.Scrapper
import javax.inject.Inject

class MangaRepoIml @Inject constructor(
    private val remote: Scrapper,
    private val dao: MangaDao,
    private val db: AppDatabase,
) : MangaRepo {

    override fun getTop() = networkBoundResource(
        query = {
            dao.getAll()
        },
        fetch = {
            remote.getTopManga()
        },
        saveFetchResult = {
            db.withTransaction {
                dao.insert(it)
            }
        }
    )

    override fun getMangas(section: SectionRoute, page: Int) = networkBoundResource(
        query = { dao.getAll() },
        fetch = { remote.getMangas(section, page) },
        saveFetchResult = {
            db.withTransaction {
                dao.insert(it)
            }
        }
    )

}