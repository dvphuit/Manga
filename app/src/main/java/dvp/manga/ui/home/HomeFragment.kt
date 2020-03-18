package dvp.manga.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import dvp.manga.databinding.HomeFragmentBinding
import dvp.manga.utils.Injector


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels {
        Injector.getHomeVMFactory(requireActivity().application, requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = HomeFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = MangaAdapter()
        binding.mangaList.adapter = adapter
        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: MangaAdapter) {
        viewModel.mangas.observe(viewLifecycleOwner) { mangas ->
            adapter.submitList(mangas)
        }
    }

}
