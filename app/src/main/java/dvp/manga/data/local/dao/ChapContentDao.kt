package dvp.manga.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dvp.manga.data.model.ChapContent

/**
 * @author dvphu on 10,March,2020
 */

@Dao
abstract class ChapContentDao : BaseDao<ChapContent>() {

    @Query("SELECT * FROM chap_content WHERE chap_id = :chap_id")
    abstract fun getPages(chap_id: Int): LiveData<List<ChapContent>>
}