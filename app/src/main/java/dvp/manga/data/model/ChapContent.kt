package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * @author dvphu on 10,March,2020
 */

@Entity(tableName = "chap_content")
data class ChapContent(
    @PrimaryKey var id: Int,
    var chap_id: Int,
    var img_url: String
)