package dvp.manga.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import dvp.manga.R
import dvp.manga.databinding.MangaDetailFragmentBinding
import dvp.manga.ui.adapter.ChapAdapter
import dvp.manga.utils.Injector


class MangaDetailFragment : Fragment() {

    private lateinit var binding: MangaDetailFragmentBinding
    private val args by navArgs<MangaDetailFragmentArgs>()

    private val viewModel: MangaDetailVM by viewModels {
        Injector.getMangaDetailVMFactory(requireContext(), args.manga)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = TransitionInflater.from(context).inflateTransition(R.transition.detail_enter)
        returnTransition = TransitionInflater.from(context).inflateTransition(R.transition.detail_return)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.detail_shared_elements)
        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.detail_shared_elements)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MangaDetailFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.apply {
            ViewCompat.setTransitionName(imgWrapper, "cover_${args.manga.name}")
            data = viewModel.manga
            chapList.adapter = ChapAdapter().apply {
                subscribeUi(this)
            }
            lifecycleOwner = this@MangaDetailFragment
        }.root
    }

    private fun subscribeUi(adapter: ChapAdapter) {
//        viewModel.state.observe(viewLifecycleOwner) {
//            when (it!!) {
//                ViewState.LOADING -> Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_SHORT).show()
//                ViewState.SUCCESS -> {
//                    Toast.makeText(requireContext(), "SUCCESS", Toast.LENGTH_SHORT).show()
//                    adapter.submitList(viewModel.chaps)
//                    Log.d("TEST","set adapter")
//                }
//                ViewState.EMPTY -> Toast.makeText(requireContext(), "EMPTY", Toast.LENGTH_SHORT).show()
//                ViewState.ERROR -> Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
//            }
//        }
        viewModel.chapters.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}
