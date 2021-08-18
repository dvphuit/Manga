package dvp.app.azmanga.data.entities

import androidx.room.*
import androidx.room.Entity
import dvp.app.azmanga.base.BaseEntity
import dvp.app.azmanga.utils.hash
import java.io.Serializable
import java.text.DecimalFormat


/**
 * @author dvphu on 10,March,2020
 */

@Entity(tableName = "manga")
data class Manga(
    var host: String? = null,
    var name: String = "",
    var cover: String? = null,
    var href: String? = null,
    @TypeConverters(GenreConverter::class)
    var genres: List<Genre> = emptyList(),
    var status: String? = null,
    var description: String? = null,
    var last_chap: String? = null
) : BaseEntity() {
    @PrimaryKey
    var id: Int = name.hash

    private fun decimalFormatted(input: String?): String =
        DecimalFormat("#,###").format(input?.toIntOrNull() ?: 0) ?: "0"

    private fun getDigit(input: String?): String =
        Regex("\\d+").find(input ?: "0")?.value ?: "0"

    @ColumnInfo(name = "viewed")
    var viewed: String = ""
        get() = decimalFormatted(field)
        set(value) {
            field = getDigit(value)
        }

    @ColumnInfo(name = "followed")
    var followed: String = ""
        get() = decimalFormatted(field)
        set(value) {
            field = getDigit(value)
        }


}

//val genres = "[{\"name\":\"Action\",\"href\":\"http://truyenqq.com/the-loai/action-26.html\"},{\"name\":\"Adult\",\"href\":\"http://truyenqq.com/the-loai/adult-68.html\"},{\"name\":\"Adventure\",\"href\":\"http://truyenqq.com/the-loai/adventure-27.html\"},{\"name\":\"Anime\",\"href\":\"http://truyenqq.com/the-loai/anime-62.html\"},{\"name\":\"Chuyển Sinh\",\"href\":\"http://truyenqq.com/the-loai/chuyen-sinh-91.html\"},{\"name\":\"Cổ Đại\",\"href\":\"http://truyenqq.com/the-loai/co-dai-90.html\"},{\"name\":\"Comedy\",\"href\":\"http://truyenqq.com/the-loai/comedy-28.html\"},{\"name\":\"Comic\",\"href\":\"http://truyenqq.com/the-loai/comic-60.html\"},{\"name\":\"Demons\",\"href\":\"http://truyenqq.com/the-loai/demons-99.html\"},{\"name\":\"Detective\",\"href\":\"http://truyenqq.com/the-loai/detective-100.html\"},{\"name\":\"Doujinshi\",\"href\":\"http://truyenqq.com/the-loai/doujinshi-96.html\"},{\"name\":\"Drama\",\"href\":\"http://truyenqq.com/the-loai/drama-29.html\"},{\"name\":\"Đam Mỹ\",\"href\":\"http://truyenqq.com/the-loai/dam-my-93.html\"},{\"name\":\"Ecchi\",\"href\":\"http://truyenqq.com/the-loai/ecchi-50.html\"},{\"name\":\"Fantasy\",\"href\":\"http://truyenqq.com/the-loai/fantasy-30.html\"},{\"name\":\"Gender Bender\",\"href\":\"http://truyenqq.com/the-loai/gender-bender-45.html\"},{\"name\":\"Harem\",\"href\":\"http://truyenqq.com/the-loai/harem-47.html\"},{\"name\":\"Historical\",\"href\":\"http://truyenqq.com/the-loai/historical-51.html\"},{\"name\":\"Horror\",\"href\":\"http://truyenqq.com/the-loai/horror-44.html\"},{\"name\":\"Huyền Huyễn\",\"href\":\"http://truyenqq.com/the-loai/huyen-huyen-468.html\"},{\"name\":\"Isekai\",\"href\":\"http://truyenqq.com/the-loai/isekai-85.html\"},{\"name\":\"Josei\",\"href\":\"http://truyenqq.com/the-loai/josei-54.html\"},{\"name\":\"Mafia\",\"href\":\"http://truyenqq.com/the-loai/mafia-69.html\"},{\"name\":\"Magic\",\"href\":\"http://truyenqq.com/the-loai/magic-58.html\"},{\"name\":\"Manhua\",\"href\":\"http://truyenqq.com/the-loai/manhua-35.html\"},{\"name\":\"Manhwa\",\"href\":\"http://truyenqq.com/the-loai/manhwa-49.html\"},{\"name\":\"Martial Arts\",\"href\":\"http://truyenqq.com/the-loai/martial-arts-41.html\"},{\"name\":\"Mature\",\"href\":\"http://truyenqq.com/the-loai/mature-48.html\"},{\"name\":\"Military\",\"href\":\"http://truyenqq.com/the-loai/military-101.html\"},{\"name\":\"Mystery\",\"href\":\"http://truyenqq.com/the-loai/mystery-39.html\"},{\"name\":\"Ngôn Tình\",\"href\":\"http://truyenqq.com/the-loai/ngon-tinh-87.html\"},{\"name\":\"One shot\",\"href\":\"http://truyenqq.com/the-loai/one-shot-95.html\"},{\"name\":\"Psychological\",\"href\":\"http://truyenqq.com/the-loai/psychological-40.html\"},{\"name\":\"Romance\",\"href\":\"http://truyenqq.com/the-loai/romance-36.html\"},{\"name\":\"School Life\",\"href\":\"http://truyenqq.com/the-loai/school-life-37.html\"},{\"name\":\"Sci-fi\",\"href\":\"http://truyenqq.com/the-loai/sci-fi-43.html\"},{\"name\":\"Seinen\",\"href\":\"http://truyenqq.com/the-loai/seinen-42.html\"},{\"name\":\"Shoujo\",\"href\":\"http://truyenqq.com/the-loai/shoujo-38.html\"},{\"name\":\"Shoujo Ai\",\"href\":\"http://truyenqq.com/the-loai/shoujo-ai-98.html\"},{\"name\":\"Shounen\",\"href\":\"http://truyenqq.com/the-loai/shounen-31.html\"},{\"name\":\"Shounen Ai\",\"href\":\"http://truyenqq.com/the-loai/shounen-ai-86.html\"},{\"name\":\"Slice of life\",\"href\":\"http://truyenqq.com/the-loai/slice-of-life-46.html\"},{\"name\":\"Smut\",\"href\":\"http://truyenqq.com/the-loai/smut-97.html\"},{\"name\":\"Sports\",\"href\":\"http://truyenqq.com/the-loai/sports-57.html\"},{\"name\":\"Supernatural\",\"href\":\"http://truyenqq.com/the-loai/supernatural-32.html\"},{\"name\":\"Tragedy\",\"href\":\"http://truyenqq.com/the-loai/tragedy-52.html\"},{\"name\":\"Trọng Sinh\",\"href\":\"http://truyenqq.com/the-loai/trong-sinh-82.html\"},{\"name\":\"Truyện Màu\",\"href\":\"http://truyenqq.com/the-loai/truyen-mau-92.html\"},{\"name\":\"Webtoon\",\"href\":\"http://truyenqq.com/the-loai/webtoon-55.html\"},{\"name\":\"Xuyên Không\",\"href\":\"http://truyenqq.com/the-loai/xuyen-khong-88.html\"},{\"name\":\"Yaoi\",\"href\":\"http://truyenqq.com/the-loai/yaoi-70.html\"},{\"name\":\"Yuri\",\"href\":\"http://truyenqq.com/the-loai/yuri-75.html\"},{\"name\":\"Top Ngày\",\"href\":\"http://truyenqq.com/top-ngay.html\"},{\"name\":\"Top Tuần\",\"href\":\"http://truyenqq.com/top-tuan.html\"},{\"name\":\"Top Tháng\",\"href\":\"http://truyenqq.com/top-thang.html\"},{\"name\":\"Yêu Thích\",\"href\":\"http://truyenqq.com/truyen-yeu-thich.html\"},{\"name\":\"Mới Cập Nhật\",\"href\":\"http://truyenqq.com/truyen-moi-cap-nhat.html\"},{\"name\":\"Truyện Mới\",\"href\":\"http://truyenqq.com/truyen-tranh-moi.html\"},{\"name\":\"Truyện Full\",\"href\":\"http://truyenqq.com/truyen-hoan-thanh.html\"}]"

data class Genre(val text: String, val href: String) : Serializable {
    override fun toString() = text
}


class MangaInfo {
    @Embedded
    var manga: Manga? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "manga_id"
    )
    var metaData: MetaData? = null
}