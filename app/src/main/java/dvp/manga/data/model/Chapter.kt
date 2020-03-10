package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapter")
data class Chapter(
    @PrimaryKey var id: Int,
    var manga_id: Int,
    var name: String? = null,
    var href: String? = null
)