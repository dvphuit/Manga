package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "page")
data class Page(
    @PrimaryKey var id: Int,
    var chap_id: Int,
    var img_url: String
)