package dvp.app.azmanga.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dvp.app.azmanga.data.remote.Scrapper
import dvp.app.azmanga.data.remote.TruyenQQ

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideScrapper(): Scrapper {
        return TruyenQQ()
    }

    @Provides
    fun provideTruyenQQ(): TruyenQQ {
        return TruyenQQ()
    }

}