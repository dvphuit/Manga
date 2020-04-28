package dvp.manga.ui.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import dvp.manga.databinding.StoryFragmentBinding
import dvp.manga.ui.ViewState
import dvp.manga.ui.adapter.ChapContentAdapter
import dvp.manga.utils.Injector


class StoryFragment : Fragment() {

    private val args by navArgs<StoryFragmentArgs>()

    private val viewModel: StoryVM by viewModels {
        Injector.getChapContentVMFactory(requireActivity().application, requireContext(), args.chap)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = StoryFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.apply {
            storyList.setHasFixedSize(true)
            storyList.adapter = ChapContentAdapter().apply {
                subscribeUi(this)
            }
        }.root
    }

    private fun subscribeUi(adapter: ChapContentAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it!!) {
                ViewState.LOADING -> Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_SHORT).show()
                ViewState.SUCCESS -> adapter.submitList(viewModel.contents)
                ViewState.EMPTY -> Toast.makeText(requireContext(), "EMPTY", Toast.LENGTH_SHORT).show()
                ViewState.ERROR -> Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
