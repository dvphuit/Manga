package dvp.manga.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dvp.manga.MainActivity
import dvp.manga.databinding.FragmentExploreBinding
import dvp.manga.ui.adapter.GenreMangaPagerAdapter
import dvp.manga.utils.SharedElementManager

class ExploreFragment : Fragment() {

    companion object {
        var elementPosition = 0
        var elementName = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentExploreBinding.inflate(inflater, container, false)
        context ?: return binding.root
        SharedElementManager.postSE(this)
        return binding.apply {
            val adapter = GenreMangaPagerAdapter(childFragmentManager, lifecycle)
            pagerGenre.adapter = adapter
            TabLayoutMediator(
                tabGenre,
                pagerGenre,
                TabLayoutMediator.TabConfigurationStrategy { tab, position -> tab.text = adapter.getTitle(position) }
            ).attach()
        }.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBotBar()
    }
}
