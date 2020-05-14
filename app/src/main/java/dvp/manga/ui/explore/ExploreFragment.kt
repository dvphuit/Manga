package dvp.manga.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.tabs.TabLayoutMediator
import dvp.manga.MainActivity
import dvp.manga.data.model.SectionRoute
import dvp.manga.databinding.FragmentExploreBinding
import dvp.manga.ui.adapter.GenreMangaPagerAdapter
import dvp.manga.ui.home.HomeFragmentDirections
import dvp.manga.utils.SharedElementManager


class ExploreFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentExploreBinding.inflate(inflater, container, false)
        context ?: return binding.root
        SharedElementManager.postSE(this)
        SharedElementManager.startSE(SectionRoute.SEARCH)

        return binding.apply {
            searchback.setOnClickListener {
                SharedElementManager.setRoute(SectionRoute.SEARCH)
                gotoSearch(searchback, searchBar)
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

    private fun gotoSearch(vararg views: View) {
        val extras = FragmentNavigatorExtras(
            views[0] to views[0].transitionName,
            views[1] to views[1].transitionName
        )
        val direction = HomeFragmentDirections.gotoSearch()
        requireView().findNavController().navigate(direction, extras)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBotBar()
    }
}
