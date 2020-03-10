package dvp.manga.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Query
import dvp.manga.data.model.Page

/**
 * @author dvphu on 10,March,2020
 */

abstract class PageDao : BaseDao<Page>() {

    @Query("SELECT * FROM page WHERE chap_id = :chap_id")
    abstract fun getPages(chap_id: Int): LiveData<List<Page>>
}