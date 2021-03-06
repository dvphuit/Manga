package dvp.manga.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dvp.manga.data.local.dao.ChapContentDao
import dvp.manga.data.local.dao.ChapterDao
import dvp.manga.data.local.dao.MangaDao
import dvp.manga.data.local.dao.MetaDataDao
import dvp.manga.data.model.*

@Database(entities = [Manga::class, Chapter::class, ChapContent::class, MetaData::class], version = 1, exportSchema = false)
@TypeConverters(GenreConverter::class, DateConverter::class)
abstract class MangaDatabase : RoomDatabase() {

    abstract fun mangaDao(): MangaDao
    abstract fun chapDao(): ChapterDao
    abstract fun chapContentDao(): ChapContentDao
    abstract fun metaDao(): MetaDataDao

    companion object {
        private const val DB_NAME = "manga.db"

        @Volatile
        private var instance: MangaDatabase? = null

        fun getInstance(ctx: Context) = instance ?: synchronized(this) {
            instance ?: buildDB(ctx).also { instance = it }
        }

        private fun buildDB(ctx: Context): MangaDatabase {
            return Room.databaseBuilder(ctx, MangaDatabase::class.java, DB_NAME).build()
        }
    }


}