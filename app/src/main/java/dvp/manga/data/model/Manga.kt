package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * @author dvphu on 10,March,2020
 */

@Entity(tableName = "manga")
data class Manga(
    @PrimaryKey var id: Int,
    var host: String? = null,
    var name: String? = null,
    var cover: String? = null,
    var href: String? = null,
    var genres: String? = null
)