package dvp.manga.utils

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import dvp.manga.data.model.Chapter
import dvp.manga.data.model.Manga
import dvp.manga.data.model.SectionDetail
import dvp.manga.ui.detail.MangaDetailFragmentDirections
import dvp.manga.ui.home.HomeFragmentDirections

/**
 * @author dvphu on 14,May,2020
 */

object NavManager {

    private var navController:NavController? = null
    fun setNavController(navController: NavController){
        this.navController = navController
    }

    fun gotoMangaDetail(section: String, manga: Manga, vararg sharedElements: View) {
        navController?.navigate(
            HomeFragmentDirections.actionMangaToDetail(manga, section),
            buildSE(*sharedElements)
        )
    }

    fun gotoSearch(vararg sharedElements: View) {
        val direction = HomeFragmentDirections.gotoSearch()
        navController?.navigate(
            direction,
            buildSE(*sharedElements)
        )
    }

    fun gotoSection(detail: SectionDetail, vararg sharedElements: View) {
        navController?.navigate(
            HomeFragmentDirections.gotoSection(detail),
            buildSE(*sharedElements)
        )
    }

    fun gotoReadChap(chap: Chapter) {
        navController?.navigate(
            MangaDetailFragmentDirections.actionChapToStory(chap)
        )
    }

    private fun buildSE(vararg views: View): FragmentNavigator.Extras {
        val pairs = views.map { it to it.transitionName }.toTypedArray()
        return FragmentNavigatorExtras(*pairs)
    }
}