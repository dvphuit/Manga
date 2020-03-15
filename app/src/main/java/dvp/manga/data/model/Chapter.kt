package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * @author dvphu on 10,March,2020
 */

@Entity(tableName = "chapter")
data class Chapter(
    @PrimaryKey var id: Int,
    var manga_id: Int,
    var name: String? = null,
    var href: String? = null
)