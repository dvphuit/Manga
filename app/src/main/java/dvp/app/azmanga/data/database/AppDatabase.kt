package dvp.app.azmanga.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dvp.app.azmanga.data.database.dao.MangaDao
import androidx.room.TypeConverters
import dvp.app.azmanga.data.entities.*

@Database(entities = [Manga::class, Chapter::class, ChapContent::class, MetaData::class], version = 1, exportSchema = false)
@TypeConverters(GenreConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun mangaDao() : MangaDao
}