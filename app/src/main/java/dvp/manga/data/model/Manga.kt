package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


/**
 * @author dvphu on 10,March,2020
 */

@Entity(tableName = "manga")
data class Manga(
    var host: String? = null,
    var name: String? = null,
    var cover: String? = null,
    var href: String? = null,
    var genres: List<Genres>? = null,
    var viewed: String? = null,
    var followed: String? = null,
    var status: String? = null,
    var info: Info? = null,
    var last_chap: String? = null
) : dvp.manga.data.model.Entity(), Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class Info(var status: String? = null, var followed: String? = null, var viewed: String? = null, var description: String? = null) : Serializable

data class Genres(val text: String, val href: String) : Serializable {
    override fun toString() = text
}