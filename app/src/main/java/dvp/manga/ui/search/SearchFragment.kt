package dvp.manga.ui.search

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import dvp.manga.R
import dvp.manga.data.model.Manga
import dvp.manga.databinding.ActivitySearchBinding
import dvp.manga.ui.Result
import dvp.manga.ui.adapter.MangaAdapter
import dvp.manga.utils.Injector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels {
        Injector.getSearchVMFactory(requireContext())
    }

    private lateinit var adapter: MangaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.search_enter)
//        returnTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.search_return)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.search_shared_enter)
        sharedElementReturnTransition   = TransitionInflater.from(requireContext()).inflateTransition(R.transition.search_shared_return)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ActivitySearchBinding.inflate(inflater, container, false)
        context ?: return binding.root
        with(binding) {
            adapter = MangaAdapter(mangaList)
            mangaList.adapter = adapter
            subscribeUi(adapter)
            adapter.registerLazyCallback { viewModel.loadMore() }
            searchView.doAfterTextChanged { searchFor(it.toString()) }
        }
        return binding.root
    }

    private fun searchFor(query: String) {
        if (viewModel.isQueryChanged(query)) {
            adapter.resetLazyList()
            viewModel.submitQuery(query)
        }
    }

    private fun subscribeUi(adapter: MangaAdapter) {
        viewModel.state.observe(this) {
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
                    Toast.makeText(requireContext(), "Must be over 3 characters", Toast.LENGTH_SHORT).show()
                    adapter.submitData(emptyList(), false)
                }
            }
        }
    }


}