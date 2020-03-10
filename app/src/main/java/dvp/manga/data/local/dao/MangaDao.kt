package dvp.manga.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Query
import dvp.manga.data.model.Manga

/**
 * @author dvphu on 10,March,2020
 */

abstract class MangaDao : BaseDao<Manga>() {
    @Query("SELECT * FROM manga")
    abstract fun getMangas(): LiveData<List<Manga>>

    @Query("SELECT * FROM manga WHERE genres LIKE '%'||:genres||'%'")
    abstract fun getMangasByGenres(genres: String): LiveData<List<Manga>>

    @Query("SELECT * FROM manga WHERE host = :host")
    abstract fun getMangasByHost(host: String): LiveData<List<Manga>>

    @Query("SELECT * FROM manga WHERE id = :id")
    abstract fun getMangaInfo(id: Int): LiveData<Manga>
}