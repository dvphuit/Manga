package dvp.manga.ui.search

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import dvp.manga.R
import dvp.manga.data.model.Manga
import dvp.manga.data.model.SectionRoute
import dvp.manga.databinding.ActivitySearchBinding
import dvp.manga.ui.FetchResult
import dvp.manga.ui.adapter.MangaAdapter
import dvp.manga.ui.base.BaseFragment
import dvp.manga.utils.Injector
import dvp.manga.utils.SharedElementManager
import dvp.manga.utils.showKeyboard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class SearchFragment : BaseFragment() {

    private val viewModel: SearchViewModel by viewModels {
        Injector.getSearchVMFactory(requireContext())
    }

    private lateinit var adapter: MangaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.search_enter)
        returnTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.search_return)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.search_shared_enter)
        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.search_shared_return)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ActivitySearchBinding.inflate(inflater, container, false)
        context ?: return binding.root
        SharedElementManager.postSE(this)
        return binding.apply {
            SharedElementManager.startSE(mangaList)
            adapter = MangaAdapter(mangaList, SectionRoute.SEARCH).apply {
                mangaList.adapter = this
                subscribeUi(this)
                registerLazyCallback { viewModel.loadMore() }
            }
            searchView.apply {
                doAfterTextChanged { searchFor(it.toString()) }
                requestFocus()
                showKeyboard()
            }
            searchback.setOnClickListener { backPressed() }
        }.root
    }

    private fun searchFor(query: String) {
        if (viewModel.isQueryChanged(query)) {
            adapter.resetLazyList()
            viewModel.submitQuery(query)
        }
    }

    private fun subscribeUi(adapter: MangaAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is FetchResult.Success -> {
                    @Suppress("UNCHECKED_CAST")
                    adapter.submitData(it.data as List<Manga>, it.hasNext)
                }
                is FetchResult.Empty -> {
                    adapter.setNoMoreData()
                }
                is FetchResult.Error -> {
                    Toast.makeText(requireContext(), it.errMsg, Toast.LENGTH_SHORT).show()
                }
                is FetchResult.EmptyQuery -> {
                    Toast.makeText(requireContext(), "Must be over 3 characters", Toast.LENGTH_SHORT).show()
                    adapter.submitData(emptyList(), false)
                }
            }
        }
    }


    override val withoutBotNav: Boolean = true
}