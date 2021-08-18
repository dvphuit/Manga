package dvp.app.azmanga.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import dvp.app.azmanga.utils.hash

@Entity(tableName = "chapter")
data class Chapter(
    val manga_id: Int,
    val index: Float,
    val name: String,
    val href: String,
    val upload_date: String
) {
    @PrimaryKey
    var id: Int = (name + manga_id).hash
}