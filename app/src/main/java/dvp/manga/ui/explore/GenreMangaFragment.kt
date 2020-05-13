package dvp.manga.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import dvp.manga.data.model.Manga
import dvp.manga.data.model.SectionRoute
import dvp.manga.databinding.FragmentGenreMangaBinding
import dvp.manga.ui.FetchResult
import dvp.manga.ui.adapter.MangaAdapter
import dvp.manga.utils.Injector

/**
 * A simple [Fragment] subclass.
 */
class GenreMangaFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(genre: SectionRoute) = GenreMangaFragment().apply {
            arguments = Bundle().apply {
                putSerializable("genre", genre)
            }
        }
    }

    private lateinit var section: SectionRoute

    private val viewModel: ExploreModel by viewModels {
        Injector.getExploreVMFactory(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        section = arguments?.get("genre") as SectionRoute
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGenreMangaBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.apply {
            mangaList.adapter = MangaAdapter(mangaList, section).apply {
                resetLazyList()
                registerLazyCallback { viewModel.loadMore() }
                subscribeUi(this)
            }
        }.root
    }

    private fun subscribeUi(adapter: MangaAdapter) {
        viewModel.getState(section).observe(viewLifecycleOwner) {
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
                    adapter.submitData(emptyList(), false)
                }
            }
        }
    }
}
