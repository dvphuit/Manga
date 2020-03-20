package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * @author dvphu on 10,March,2020
 */

@Entity(tableName = "chapter")
data class Chapter(
    var manga_id: Int = 0,
    var name: String? = null,
    var href: String? = null
) : Serializable {
    @PrimaryKey
    var id: Int = 0
}