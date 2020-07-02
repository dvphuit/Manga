package dvp.manga.data.local.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import dvp.manga.data.model.Manga
import dvp.manga.data.model.MetaData


/**
 * @author dvphu on 20,May,2020
 */

@Dao
abstract class MetaDataDao : BaseDao<MetaData>() {
    @Query("UPDATE meta_data SET slug = slug || :slug || '|' WHERE manga_id IN (SELECT manga_id FROM meta_data WHERE manga_id IN (:ids) AND (slug = '' OR slug NOT LIKE '%' || :slug || '%'))")
    abstract fun updateSlug(slug: String, ids: List<Int>)

    @Query("SELECT manga_id FROM meta_data WHERE slug = '' OR slug NOT LIKE '%' || :slug || '%'")
    abstract fun getMangaIdsNoSlug(slug: String): List<Int>

    @Query("SELECT * FROM meta_data WHERE manga_id in (:mangaIds) AND (slug = '' OR slug NOT LIKE '%' || :slug || '%')")
    abstract fun getMetaDatas(mangaIds: List<Int>, slug: String): List<MetaData>

    suspend fun upsertMetaSlug(mangas: List<Manga>, slug: String) {
        val metas = getMetaDatas(
            mangaIds = mangas.map { it.id },
            slug = slug
        )
        val newMetas = mangas.map {
            MetaData(
                manga_id = it.id,
                bookmarked = false,
                slug = slug
            )
        }
        val group = (newMetas + metas)
            .groupBy { it.manga_id }
            .map { entry ->
                MetaData(
                    manga_id = entry.key,
                    bookmarked = entry.value.last().bookmarked,
                    slug = entry.value.joinToString("|") { it.slug }
                )
            }
        upsert(group)
    }

}