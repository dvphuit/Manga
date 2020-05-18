package dvp.manga.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dvp.manga.data.model.SectionRoute
import dvp.manga.ui.explore.GenreMangaFragment


/**
 * @author dvphu on 12,May,2020
 */

class GenreMangaPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val genres = SectionRoute.values().takeLast(SectionRoute.values().size - 6)

    override fun createFragment(position: Int): Fragment {
        return GenreMangaFragment.newInstance(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    fun getTitle(position: Int) = genres[position].value
}