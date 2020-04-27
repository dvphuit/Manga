package dvp.manga.ui.home

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import dvp.manga.data.model.Manga
import dvp.manga.data.model.MangaSection
import dvp.manga.data.model.Section
import dvp.manga.data.model.Top
import dvp.manga.databinding.HomeFragmentBinding
import dvp.manga.ui.Result
import dvp.manga.ui.adapter.HomeAdapter
import dvp.manga.ui.adapter.MangaAdapter
import dvp.manga.ui.adapter.TopMangaAdapter
import dvp.manga.ui.base.BaseFragment
import dvp.manga.utils.Injector
import dvp.manga.utils.delayForSharedElement


class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels {
        Injector.getHomeVMFactory(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = HomeFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root
        postponeEnterTransition()
        return binding.apply {
            searchback.setOnClickListener {
                gotoSearch(searchback, searchBar)
            }
            recyclerView.adapter = HomeAdapter(this@HomeFragment).apply {
                submitData(prepareData())
            }
            recyclerView.delayForSharedElement(this@HomeFragment)
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

    private fun prepareData(): List<Section> {
        return with(viewModel) {
            arrayListOf(
                Top(topMangas),
                MangaSection("Most favourite", favourite),
                MangaSection("Last updated", lastUpdated),
                MangaSection("For Boy", forBoy),
                MangaSection("For Girl", forGirl)
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

    private fun subscribeTopManga(topManga: ViewPager2, adapter: TopMangaAdapter) {
        viewModel.topMangas.observe(viewLifecycleOwner) {
            adapter.submitData(it)
            topManga.currentItem = it.size / 2
        }
    }

    private fun subscribeUi(adapter: MangaAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    @Suppress("UNCHECKED_CAST")
                    adapter.submitData(it.data as List<Manga>, it.hasNext)
                    Log.d("TEST", "state success ${it.data.size}")
                }
                is Result.Empty -> {
                    Log.d("TEST", "state empty")
                    adapter.setNoMoreData()
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), it.errMsg, Toast.LENGTH_SHORT).show()
                    Log.d("TEST", "state error")
                }
                is Result.EmptyQuery -> {
                    Toast.makeText(
                        requireContext(),
                        "Must be over 3 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                    adapter.submitData(emptyList(), false)
                }
            }
        }
    }
}
