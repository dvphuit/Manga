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
import dvp.manga.MainActivity
import dvp.manga.databinding.StoryFragmentBinding
import dvp.manga.ui.ResultData
import dvp.manga.ui.adapter.ChapContentAdapter
import dvp.manga.utils.Injector


class StoryFragment : Fragment() {

    private val args by navArgs<StoryFragmentArgs>()

    private val viewModel: StoryVM by viewModels {
        Injector.getChapContentVMFactory(requireActivity().application, requireContext(), args.chap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).hideBotBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showBotBar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = StoryFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.root.elevation = 20f
        postponeEnterTransition()
        return binding.apply {
            storyList.setHasFixedSize(true)
            storyList.adapter = ChapContentAdapter().apply {
                subscribeUi(this)
            }
        }.root
    }

    private fun subscribeUi(adapter: ChapContentAdapter) {
        viewModel.contents.observe(viewLifecycleOwner){
            when(it){
                is ResultData.Success -> {
                    adapter.submitList(it.value)
                    startPostponedEnterTransition()
                }
                is ResultData.Failure -> {
                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                }
                is ResultData.Loading ->{
                    Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
