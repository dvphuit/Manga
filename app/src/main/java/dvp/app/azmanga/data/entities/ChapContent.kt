package dvp.app.azmanga.data.entities

import androidx.room.PrimaryKey
import androidx.room.Entity


@Entity(tableName = "chap_content")
data class ChapContent(
    var chap_id: Int = 0,
    var img_url: String? = null
) {
    @PrimaryKey
    var id: Int = 0
}