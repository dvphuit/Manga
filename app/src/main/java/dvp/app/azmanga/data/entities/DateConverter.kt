package dvp.app.azmanga.data.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * @author dvphu on 11,May,2020
 */

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

class GenreConverter {
    @TypeConverter
    fun toGenres(json: String): List<Genre> {
        val type = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(genres: List<Genre>): String {
        val type = object : TypeToken<List<Genre>>() {}.type
        return Gson().toJson(genres, type)
    }
}