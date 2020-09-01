package dvp.manga.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.MainActivity
import dvp.manga.R
import dvp.manga.data.model.MangaSection
import dvp.manga.data.model.Section
import dvp.manga.data.model.SectionRoute
import dvp.manga.data.model.Top
import dvp.manga.databinding.FragmentBookmarkBinding
import dvp.manga.ui.adapter.HomeAdapter
import dvp.manga.ui.base.BaseFragment
import dvp.manga.ui.home.HomeViewModel
import dvp.manga.utils.Injector
import dvp.manga.utils.NavManager
import dvp.manga.utils.SharedElementManager
import kotlinx.android.synthetic.main.search_bar.view.*

/**
 * A simple [Fragment] subclass.
 */
class BookmarkFragment : BaseFragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private val viewModel: HomeViewModel by viewModels {
        Injector.getHomeVMFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        context ?: return binding.root
        if (viewModel.isInitialized) {
            SharedElementManager.postSE(this)
        }
        SharedElementManager.startSE(SectionRoute.SEARCH)

        return binding.apply {
            initSearchBar(binding)
            lvRecent.setHasFixedSize(true)
            lvRecent.adapter = HomeAdapter(this@BookmarkFragment).apply {
                submitData(prepareSection())
            }
            //smooth scroll multiple recyclerview
            lvRecent.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
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

    private fun initSearchBar(binding: FragmentBookmarkBinding){
        with(binding.toolbar){
            title.text = "Library"
            search_back.setOnClickListener {
                SharedElementManager.setRoute(SectionRoute.SEARCH)
                NavManager.gotoSearch(search_back, search_bar, binding.toolbar)
            }
        }
    }
    //TODO: will fix hardcode
    private fun prepareSection(): List<Section> {
        viewModel.isInitialized = true
        return with(viewModel) {
            arrayListOf(
                MangaSection(SectionRoute.FAVOURITE, favourite, recyclerViewsState[1])
            )
        }
    }

    override fun onPause() {
        super.onPause()
//        saveSectionsState()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBotBar()
    }

//    private fun saveSectionsState() {
//        with(binding.recyclerView) {
//            loop@ for (i in 1..adapter!!.itemCount) {
//                val child = findViewHolderForAdapterPosition(i) ?: continue@loop
//                val mangaList = child.itemView.findViewById<RecyclerView>(R.id.manga_list)
//                viewModel.recyclerViewsState[i] = mangaList.layoutManager!!.onSaveInstanceState()
//            }
//        }
//    }

    override val withoutBotNav: Boolean = false
}
