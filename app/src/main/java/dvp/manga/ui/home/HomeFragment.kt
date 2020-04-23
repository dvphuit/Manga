package dvp.manga.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import dvp.manga.data.model.Manga
import dvp.manga.databinding.HomeFragmentBinding
import dvp.manga.ui.Result
import dvp.manga.ui.adapter.MangaAdapter
import dvp.manga.ui.adapter.TopMangaAdapter
import dvp.manga.ui.base.BaseFragment
import dvp.manga.utils.Injector
import dvp.manga.utils.delayForSharedElement
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlin.math.abs


@FlowPreview
@ExperimentalCoroutinesApi
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels {
        Injector.getHomeVMFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = HomeFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.apply {
            mangaList.delayForSharedElement(this@HomeFragment)
            mangaList.adapter = MangaAdapter(mangaList).apply {
                registerLazyCallback { viewModel.loadMore() }
                if (!viewModel.isInitialized) {
                    resetLazyList()
                }
                subscribeUi(this)
            }
            searchback.setOnClickListener {
                gotoSearch(searchback, searchBar)
            }

            topMangaList.apply {
                clipChildren = false
                clipToPadding = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                val transform = CompositePageTransformer()
                transform.addTransformer(MarginPageTransformer(40))
                transform.addTransformer { page, position ->
                    val r = 1 - abs(position)
                    page.scaleY = (.85 + .15 * r).toFloat()
                }
                setPageTransformer(transform)

                adapter = TopMangaAdapter().apply {
                    subscribeTopManga(this)
                }
            }
        }.root
    }

    private fun gotoSearch(vararg views: View) {
        val extras = FragmentNavigatorExtras(
            views[0] to views[0].transitionName,
            views[1] to views[1].transitionName
        )
        val direction = HomeFragmentDirections.gotoSearch()
        views[0].findNavController().navigate(direction, extras)
    }

    private fun subscribeTopManga(adapter: TopMangaAdapter) {
        viewModel.topMangas.observe(viewLifecycleOwner) {
            adapter.submitData(it)
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
