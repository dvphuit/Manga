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
import kotlinx.android.synthetic.main.search_bar.view.*


class ExploreFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentExploreBinding.inflate(inflater, container, false)
        context ?: return binding.root
        SharedElementManager.postSE(this)
        SharedElementManager.startSE(SectionRoute.SEARCH)

        return binding.apply {
            initSearchBar(this)
            val adapter = GenreMangaPagerAdapter(childFragmentManager, lifecycle)
            pagerGenre.adapter = adapter
            TabLayoutMediator(
                tabGenre,
                pagerGenre,
                TabLayoutMediator.TabConfigurationStrategy { tab, position -> tab.text = adapter.getTitle(position) }
            ).attach()
        }.root
    }

    private fun initSearchBar(binding: FragmentExploreBinding){
        with(binding.toolbar){
            title.text = "Explore"
            search_back.setOnClickListener {
                SharedElementManager.setRoute(SectionRoute.SEARCH)
                NavManager.gotoSearch(search_back, search_bar, this)
            }
        }
    }

    override val withoutBotNav: Boolean = false
}
