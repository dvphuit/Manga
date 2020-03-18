package dvp.manga.utils

import android.content.Context
import dvp.manga.data.remote.TruyenQQ
import dvp.manga.data.repository.MangaRepository
import dvp.manga.ui.home.HomeVMFactory

/**
 * @author dvphu on 18,March,2020
 */

object Injector {

    private fun getMangaRepository(context: Context) = MangaRepository.getInstance(
//        MangaDatabase.getInstance(context)!!.mangaDao(),
        TruyenQQ.getInstance()
    )

    fun getHomeVMFactory(context: Context) = HomeVMFactory(getMangaRepository(context))
}