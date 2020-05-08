package dvp.manga.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.R
import dvp.manga.data.model.MangaSection
import dvp.manga.data.model.Section
import dvp.manga.data.model.SectionRoute
import dvp.manga.data.model.Top
import dvp.manga.databinding.HomeFragmentBinding
import dvp.manga.ui.adapter.HomeAdapter
import dvp.manga.ui.base.BaseFragment
import dvp.manga.utils.Injector


class HomeFragment : BaseFragment() {

    companion object {
        var navSection = ""
    }

    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels {
        Injector.getHomeVMFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root
        if (viewModel.isInitialized) {
            postponeEnterTransition()
        }
        if(navSection == "search") startPostponedEnterTransition()

        return binding.apply {
            searchback.setOnClickListener {
                navSection = "search"
                gotoSearch(searchback, searchBar)
            }
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = HomeAdapter(this@HomeFragment).apply {
                submitData(prepareSection())
            }
            //smooth scroll multiple recyclerview
            recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    if (e.action == MotionEvent.ACTION_DOWN && rv.scrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                        rv.stopScroll()
                    }
                    return false
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            })
        }.root
    }

    //TODO: will fix hardcode
    private fun prepareSection(): List<Section> {
        viewModel.isInitialized = true
        return with(viewModel) {
            arrayListOf(
                Top(top),
                MangaSection(SectionRoute.FAVOURITE, favourite, recyclerViewsState[1]),
                MangaSection(SectionRoute.LAST_UPDATE, lastUpdated, recyclerViewsState[2]),
                MangaSection(SectionRoute.FOR_BOY, forBoy, recyclerViewsState[3]),
                MangaSection(SectionRoute.FOR_GIRL, forGirl, recyclerViewsState[4])
            )
        }
    }

    private fun gotoSearch(vararg views: View) {
        val extras = FragmentNavigatorExtras(
            views[0] to views[0].transitionName,
            views[1] to views[1].transitionName
        )
        val direction = HomeFragmentDirections.gotoSearch()
        views[0].findNavController().navigate(direction, extras)
    }


    override fun onPause() {
        super.onPause()
        saveSectionsState()
    }

    private fun saveSectionsState() {
        with(binding.recyclerView) {
            loop@ for (i in 1..adapter!!.itemCount) {
                val child = findViewHolderForAdapterPosition(i) ?: continue@loop
                val mangaList = child.itemView.findViewById<RecyclerView>(R.id.manga_list)
                viewModel.recyclerViewsState[i] = mangaList.layoutManager!!.onSaveInstanceState()
            }
        }
    }
}
