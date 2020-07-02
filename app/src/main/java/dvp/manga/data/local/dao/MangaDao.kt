package dvp.manga.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import dvp.manga.data.model.Manga
import dvp.manga.data.model.MangaInfo
import dvp.manga.data.model.MetaData

/**
 * @author dvphu on 10,March,2020
 */

@Dao
abstract class MangaDao : BaseDao<Manga>() {
    @Query("SELECT * FROM manga")
    abstract fun getMangas(): LiveData<List<Manga>>

    @Query("SELECT * FROM manga WHERE genres LIKE '%'||:genres||'%'")
    abstract fun getMangasByGenres(genres: String): LiveData<List<Manga>>

    @Query("SELECT * FROM manga WHERE host = :host")
    abstract fun getMangasByHost(host: String): LiveData<List<Manga>>


//    @Query("UPDATE manga SET slug = slug || :slug || '|' WHERE id IN (SELECT id FROM manga WHERE id IN (:ids) AND (slug = '' OR slug NOT LIKE '%' || :slug || '%'))")
//    abstract fun updateSlug(slug: String, ids: List<Int>)

    @Query("SELECT * FROM manga WHERE id IN (SELECT manga_id FROM meta_data WHERE slug like '%' || :slug || '%')")
    abstract fun getMangasBySlug(slug: String): LiveData<List<Manga>>

    @Query("SELECT * FROM manga WHERE id like '%top%'")
    abstract fun getTop(): LiveData<List<Manga>>

    @Query("SELECT * FROM meta_data WHERE manga_id = :mangaId")
    abstract fun getMetaData(mangaId: Int): LiveData<MetaData>

    @Query("UPDATE meta_data SET bookmarked = :state WHERE manga_id = :mangaId")
    abstract fun setBookmark(mangaId: Int, state: Boolean)

    @Transaction
    @Query("SELECT * FROM manga WHERE id = :mangaId")
    abstract fun getMangaInf(mangaId: Int): LiveData<MangaInfo>

}