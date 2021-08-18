package dvp.app.azmanga.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import dvp.app.azmanga.base.BaseEntity

@Entity(tableName = "meta_data")
data class MetaData(
    @PrimaryKey(autoGenerate = false)
    var manga_id: Int = 0,
    var bookmarked: Boolean = false,
    var slug: String = ""
) : BaseEntity()