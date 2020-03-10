package dvp.manga.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import dvp.manga.data.model.Page

@Database(entities = [Manga::class, Chapter::class, Page::class], version = 1)
abstract class MangaDatabase : RoomDatabase() {

    companion object {
        private var instance: MangaDatabase? = null
        const val DB_NAME = "manga.db"

        fun getInstance(ctx: Context): MangaDatabase? {
            if (instance == null) {
                synchronized(MangaDatabase::class.java) {
                    if (instance == null) {
                        instance = buildDB(ctx)
                    }
                }
            }
            return instance
        }

        private fun buildDB(ctx: Context): MangaDatabase {
            return Room.databaseBuilder(ctx, MangaDatabase::class.java, DB_NAME).build()
        }
    }


}