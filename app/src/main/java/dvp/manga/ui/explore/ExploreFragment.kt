package dvp.manga.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import dvp.manga.data.model.SectionRoute
import dvp.manga.databinding.FragmentExploreBinding
import dvp.manga.ui.adapter.GenreMangaPagerAdapter
import dvp.manga.ui.base.BaseFragment
import dvp.manga.utils.NavManager
import dvp.manga.utils.SharedElementManager


class ExploreFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentExploreBinding.inflate(inflater, container, false)
        context ?: return binding.root
        SharedElementManager.postSE(this)
        SharedElementManager.startSE(SectionRoute.SEARCH)

        return binding.apply {
            searchback.setOnClickListener {
                SharedElementManager.setRoute(SectionRoute.SEARCH)
                NavManager.gotoSearch(searchBar, searchback)
            }
            val adapter = GenreMangaPagerAdapter(childFragmentManager, lifecycle)
            pagerGenre.adapter = adapter
            TabLayoutMediator(
                tabGenre,
                pagerGenre,
                TabLayoutMediator.TabConfigurationStrategy { tab, position -> tab.text = adapter.getTitle(position) }
            ).attach()
        }.root
    }

    override val withoutBotNav: Boolean = false
}
