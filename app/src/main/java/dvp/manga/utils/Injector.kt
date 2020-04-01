package dvp.manga.utils

import android.app.Application
import android.content.Context
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import dvp.manga.data.remote.TruyenQQ
import dvp.manga.data.repository.ChapContentRepository
import dvp.manga.data.repository.ChapterRepository
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.detail.MangaDetailVMFactory
import dvp.manga.ui.home.HomeVMFactory
import dvp.manga.ui.search.SearchVMFactory
import dvp.manga.ui.story.StoryVMFactory

/**
 * @author dvphu on 18,March,2020
 */

object Injector {

    private fun getMangaRepository(context: Context) = MangaRepository.getInstance(
//        MangaDatabase.getInstance(context)!!.mangaDao(),
        TruyenQQ.getInstance(context)
    )

    private fun getChapterRepository(context: Context) = ChapterRepository.getInstance(TruyenQQ.getInstance(context))

    private fun getChapContentRepository(context: Context) = ChapContentRepository.getInstance(TruyenQQ.getInstance(context))

    fun getHomeVMFactory(app: Application, context: Context) = HomeVMFactory(app, getMangaRepository(context))

    fun getMangaDetailVMFactory(app: Application, context: Context, manga: Manga) = MangaDetailVMFactory(app, getChapterRepository(context), manga)

    fun getChapContentVMFactory(app: Application, context: Context, chap: Chapter) = StoryVMFactory(app, getChapContentRepository(context), chap)

    fun getSearchVMFactory(context: Context) = SearchVMFactory(getMangaRepository(context))
}