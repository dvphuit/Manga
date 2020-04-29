package dvp.manga.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dvp.manga.data.model.Chapter

/**
 * @author dvphu on 10,March,2020
 */

@Dao
abstract class ChapterDao : BaseDao<Chapter>() {
    @Query("SELECT * FROM chapter WHERE manga_id = :manga_id")
    abstract fun getChapters(manga_id: Int): LiveData<List<Chapter>>
}