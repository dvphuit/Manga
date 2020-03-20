package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author dvphu on 10,March,2020
 */

@Entity(tableName = "chap_content")
data class ChapContent(
    var chap_id: Int = 0,
    var img_url: String? = null
) {
    @PrimaryKey
    var id: Int = 0
}