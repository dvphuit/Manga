package dvp.app.azmanga.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dvp.app.azmanga.data.database.AppDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "manga.db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideMangaDao(db: AppDatabase) = db.mangaDao()
}