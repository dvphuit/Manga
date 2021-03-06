package dvp.manga.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import dvp.manga.R
import dvp.manga.data.model.MangaInfo
import dvp.manga.databinding.MangaDetailFragmentBinding
import dvp.manga.ui.ResultData
import dvp.manga.ui.adapter.ChapPageAdapter
import dvp.manga.ui.base.BaseFragment
import dvp.manga.utils.Injector
import dvp.manga.utils.SharedElementManager
import kotlinx.coroutines.launch


class MangaDetailFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: MangaDetailFragmentBinding
    private val args by navArgs<MangaDetailFragmentArgs>()
    private var mangaInfo: MangaInfo? = null

    private val viewModel: MangaDetailVM by viewModels {
        Injector.getMangaDetailVMFactory(requireContext(), args.manga)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = TransitionInflater.from(context).inflateTransition(R.transition.detail_enter)
        returnTransition = TransitionInflater.from(context).inflateTransition(R.transition.detail_return)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.detail_shared_elements)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MangaDetailFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root
        SharedElementManager.postSE(this)
        return binding.apply {
            ViewCompat.setTransitionName(imgWrapper, "cover_${args.section}${args.manga.id}")
            ViewCompat.setTransitionName(mangaDetail, "scrim_${args.section}${args.manga.id}")
//            data = args.manga
            viewModel.manga.observe(viewLifecycleOwner) {
                mangaInfo = it
                data = it
            }
            //set up viewpager for chapters
            val adapter = ChapPageAdapter().apply { subscribeUi(this) }
            pagerChap.adapter = adapter
            TabLayoutMediator(
                tabChap,
                pagerChap,
                TabLayoutMediator.TabConfigurationStrategy { tab, position -> tab.text = adapter.getTitle(position) }
            ).attach()
            //set click listener
            btBack.setOnClickListener(this@MangaDetailFragment)
            btBookmark.setOnClickListener(this@MangaDetailFragment)
            btDownload.setOnClickListener(this@MangaDetailFragment)
        }.root
    }

    private fun subscribeUi(adapter: ChapPageAdapter) {
        viewModel.pageChaps.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Success -> {
                    adapter.submitData(it.value)
                    SharedElementManager.startSE()
                }
                is ResultData.Failure -> {
                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                }
                is ResultData.Loading -> {
                    Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btBack -> {
                backPressed()
            }
            binding.btBookmark -> {
                viewModel.setBookmark(mangaInfo!!, binding.btBookmark.isChecked)
            }
            binding.btDownload -> {
                Toast.makeText(context, "under construction", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override val withoutBotNav: Boolean = true
}
