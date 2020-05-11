package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dvp.manga.utils.hash
import java.io.Serializable

/**
 * @author dvphu on 10,March,2020
 */

@Entity(tableName = "chapter")
data class Chapter(
    val manga_id: Int,
    val index: Float,
    val name: String,
    val href: String,
    val upload_date: String
) : Serializable {
    @PrimaryKey
    var id: Int = (name + manga_id).hash
}