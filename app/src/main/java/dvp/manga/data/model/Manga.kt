package dvp.manga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DecimalFormat


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
    var info: Info? = null,
    var last_chap: String? = null
) : dvp.manga.data.model.Entity(), Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class Info(var status: String? = null, val _followed: String? = null, val _viewed: String? = null, var description: String? = null) : Serializable {
    private fun decimalFormatted(input: String): String = DecimalFormat("#,###").format(input.toInt()) ?: "0"
    private fun getDigit(input: String): String = Regex("\\d+").find(input)?.value ?: "0"

    var viewed: String = ""
        get() = decimalFormatted(field)
        set(value) {
            field = getDigit(value)
        }

    var followed: String = ""
        get() = decimalFormatted(field)
        set(value) {
            field = getDigit(value)
        }

}

data class Genres(val text: String, val href: String) : Serializable {
    override fun toString() = text
}