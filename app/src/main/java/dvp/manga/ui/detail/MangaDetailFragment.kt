package dvp.manga.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import dvp.manga.data.model.Manga
import dvp.manga.databinding.MangaDetailFragmentBinding
import dvp.manga.ui.ViewState
import dvp.manga.ui.adapter.ChapAdapter
import dvp.manga.utils.Injector

class MangaDetailFragment : Fragment() {

    private val viewModel: MangaDetailVM by viewModels {
        Injector.getMangaDetailVMFactory(requireActivity().application, requireContext(), arguments?.get("manga") as Manga)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = MangaDetailFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = ChapAdapter()
        binding.chapList.adapter = adapter
        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: ChapAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it!!) {
                ViewState.LOADING -> Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_SHORT).show()
                ViewState.SUCCESS -> adapter.submitList(viewModel.chaps)
                ViewState.EMPTY -> Toast.makeText(requireContext(), "EMPTY", Toast.LENGTH_SHORT).show()
                ViewState.ERROR -> Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
