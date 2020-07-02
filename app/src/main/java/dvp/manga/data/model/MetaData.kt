package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * @author dvphu on 20,May,2020
 */

@Entity(tableName = "meta_data")
data class MetaData(
    @PrimaryKey(autoGenerate = false)
    var manga_id: Int = 0,
    var bookmarked: Boolean = false,
    var slug: String = ""
) : dvp.manga.data.model.Entity(), Serializable


