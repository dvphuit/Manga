package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * @author dvphu on 10,March,2020
 */

@Entity(tableName = "manga")
data class Manga(
    var host: String? = null,
    var name: String? = null,
    var cover: String? = null,
    var href: String? = null,
    var genres: String? = null,
    var last_chap: String? = null
){
    @PrimaryKey(autoGenerate = true)  val id: Int = 0

}