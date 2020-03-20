package dvp.manga.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.databinding.HomeFragmentBinding
import dvp.manga.ui.ViewState
import dvp.manga.ui.adapter.MangaAdapter
import dvp.manga.utils.Injector


class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding

    private val viewModel: HomeViewModel by viewModels {
        Injector.getHomeVMFactory(requireActivity().application, requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = MangaAdapter(binding.mangaList)
        binding.mangaList.adapter = adapter
        subscribeUi(adapter)
        setLoadMoreListener(binding.mangaList)
        return binding.root
    }

    private fun subscribeUi(adapter: MangaAdapter) {
        startFetch()
        viewModel.state.observe(viewLifecycleOwner) {
            when (it!!) {
                ViewState.LOADING -> Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_SHORT).show()
                ViewState.SUCCESS -> {
                    adapter.submitData(viewModel.mangas)
                    stopFetch()
                }
                ViewState.EMPTY -> Toast.makeText(requireContext(), "EMPTY", Toast.LENGTH_SHORT).show()
                ViewState.ERROR -> Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private var isLoading = false
    private var pageIndex = 1

    private fun startFetch() {
        isLoading = true
        binding.bottomProgress.visibility = View.VISIBLE
        viewModel.fetchMangas(pageIndex)
    }

    private fun stopFetch() {
        isLoading = false
        binding.bottomProgress.visibility = View.GONE
        pageIndex++
    }

    private fun setLoadMoreListener(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy < 0 || isLoading) return
                    val totalItemCount = layoutManager.getItemCount()
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (!isLoading && lastVisibleItem >= totalItemCount - 5) {
                        startFetch()
                    }
                }
            })
        }
    }

}
