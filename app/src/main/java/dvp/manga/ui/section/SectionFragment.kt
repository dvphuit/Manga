package dvp.manga.ui.section

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import dvp.manga.R
import dvp.manga.data.model.Manga
import dvp.manga.databinding.FragmentSectionBinding
import dvp.manga.ui.Result
import dvp.manga.ui.adapter.MangaAdapter
import dvp.manga.utils.Injector
import dvp.manga.utils.delayForSharedElement
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
class SectionFragment : Fragment() {

    private val args by navArgs<SectionFragmentArgs>()

    private val viewModel: SectionViewModel by viewModels {
        Injector.getSectionVMFactory(requireContext())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        enterTransition = TransitionInflater.from(context).inflateTransition(R.transition.section_enter)
        returnTransition = TransitionInflater.from(context).inflateTransition(R.transition.section_return)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSectionBinding.inflate(inflater, container, false)
        context ?: return binding.root
        postponeEnterTransition()
        return binding.apply {
            ViewCompat.setTransitionName(parent, "parent_${args.section.title}")
            sectionTitle.text = args.section.title
            mangaList.delayForSharedElement(this@SectionFragment)
            mangaList.adapter = MangaAdapter(mangaList, args.section.title).apply {
                viewModel.initData(args.section.mangaList)
                registerLazyCallback { viewModel.loadMore() }
                subscribeUi(this)
            }
        }.root
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
