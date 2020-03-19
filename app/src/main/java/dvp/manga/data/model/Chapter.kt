package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author dvphu on 10,March,2020
 */

@Entity(tableName = "chapter")
data class Chapter(
    var manga_id: Int = 0,
    var name: String? = null,
    var href: String? = null
){
    @PrimaryKey
    var id: Int = 0
}