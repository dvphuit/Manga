package dvp.app.azmanga.data.db

import dvp.app.azmanga.data.database.dao.MangaDao
import dvp.app.azmanga.data.entities.Manga
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`

/**
 * @author PhuDV
 * Created 8/4/21 at 10:03 AM
 */
class MangaDaoTest : TestDatabase() {
    private lateinit var dao: MangaDao

    @Before
    fun init() {
        dao = db.mangaDao()
    }

    @Test
    fun test() = runBlocking {
        val manga = Manga(name = "TEST", status = "done")
        dao.insert(manga)

        val loadFromDB = dao.getAll()
        assertThat(loadFromDB.toString(), `is`(manga.toString()))
    }
}