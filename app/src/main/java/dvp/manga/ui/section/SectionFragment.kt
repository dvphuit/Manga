package dvp.manga.ui.section

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import dvp.manga.R
import dvp.manga.data.model.Manga
import dvp.manga.databinding.FragmentSectionBinding
import dvp.manga.ui.FetchResult
import dvp.manga.ui.adapter.MangaAdapter
import dvp.manga.ui.base.BaseFragment
import dvp.manga.utils.Injector
import dvp.manga.utils.SharedElementManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
class SectionFragment : BaseFragment() {

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
        SharedElementManager.postSE(this)
        return binding.apply {
            ViewCompat.setTransitionName(parent, "parent_${args.sectionDetail.section}")
            sectionTitle.text = args.sectionDetail.section.value
            SharedElementManager.startSE(mangaList)
            mangaList.adapter = MangaAdapter(mangaList, args.sectionDetail.section).apply {
                viewModel.initData(args.sectionDetail.mangaList)
                registerLazyCallback { viewModel.loadMore() }
                subscribeUi(this)
            }
        }.root
    }

    private fun subscribeUi(adapter: MangaAdapter) {
        viewModel.getState(args.sectionDetail.section).observe(viewLifecycleOwner) {
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
            }
        }
    }


    override val withoutBotNav: Boolean = true
}
