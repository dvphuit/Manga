package dvp.manga.ui.search

import android.app.SharedElementCallback
import android.graphics.Point
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionSet
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import dvp.manga.CircularReveal
import dvp.manga.R
import dvp.manga.data.remote.TruyenQQ
import dvp.manga.data.repository.MangaRepository
import dvp.manga.databinding.ActivitySearchBinding
import dvp.manga.ui.adapter.MangaAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch


class SearchActivity : AppCompatActivity() {

//    private val viewModel: SearchViewModel by viewModels {
//        Injector.getSearchVMFactory(application, this)
//    }

    private lateinit var adapter: MangaAdapter

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.Factory(
            MangaRepository.getInstance(TruyenQQ.getInstance(this)),
            Dispatchers.IO
        )
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
        adapter = MangaAdapter(binding.mangaList)
        with(adapter) {
            binding.mangaList.adapter = this
//            subscribeUi(this)
//            setQueryListener(this, binding.searchView)
            viewModel.searchResult.observe(this@SearchActivity) { data ->
                handleSearchResult(this, data)
            }
        }
        binding.searchView.doAfterTextChanged { query ->
            adapter.setLazyCallback { page ->
                lifecycleScope.launch {
                    viewModel.queryChannel.send(QueryModel(query.toString(), page))
                }
            }
        }
        setupTransitions()
    }


    private fun handleSearchResult(adapter: MangaAdapter, it: SearchResult) {
        when (it) {
            is ValidResult -> {
                adapter.addData(it.result)
            }
            is ErrorResult -> {
                println("Error result")
                Toast.makeText(this, "ErrorResult", Toast.LENGTH_SHORT).show()
            }
            is EmptyResult -> {
                Toast.makeText(this, "EmptyResult", Toast.LENGTH_SHORT).show()
                adapter.noMoreData()
            }
            is EmptyQuery -> {
                println("Empty query")
                Toast.makeText(this, "EmptyQuery", Toast.LENGTH_SHORT).show()
            }
            is TerminalError -> {
                println("Our Flow terminated unexpectedly, so we're bailing!")
                Toast.makeText(this, "Unexpected error in SearchRepository!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
//    internal class DebouncingQueryTextListener(
//        lifecycle: Lifecycle,
//        private val onDebouncingQueryTextChange: (String?) -> Unit
//    ) : SearchView.OnQueryTextListener {
//        private var debouncePeriod: Long = 500
//
//        private val coroutineScope = lifecycle.coroutineScope
//
//        private var searchJob: Job? = null
//
//        override fun onQueryTextSubmit(query: String?): Boolean {
//            return false
//        }
//
//        override fun onQueryTextChange(newText: String?): Boolean {
//            searchJob?.cancel()
//            searchJob = coroutineScope.launch {
//                newText?.let {
//                    delay(debouncePeriod)
//                    onDebouncingQueryTextChange(newText)
//                }
//            }
//            return false
//        }
//    }

//    private fun setQueryListener(adapter: MangaAdapter, searchView: EditText) {
//        searchView.addTextChangedListener(object : DelayTextChanged() {
//            override fun textWasChanged(s: String) {
//                runOnUiThread {
//                    adapter.setLazyCallback { pageIndex ->
//                        viewModel.searchManga(s, pageIndex)
//                    }
//                }
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//        })
//    }


//    private fun subscribeUi(adapter: MangaAdapter) {
//        viewModel.state.observe(this) {
//            when (it!!) {
//                ViewState.LOADING -> Toast.makeText(this, "LOADING", Toast.LENGTH_SHORT).show()
//                ViewState.SUCCESS -> adapter.submitData(viewModel.mangas)
//                ViewState.EMPTY -> Toast.makeText(this, "EMPTY", Toast.LENGTH_SHORT).show()
//                ViewState.ERROR -> Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


    private fun setupTransitions() {
        // grab the position that the search icon transitions in *from*
        // & use it to configure the return transition
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onSharedElementStart(
                sharedElementNames: List<String>,
                sharedElements: List<View>?,
                sharedElementSnapshots: List<View>
            ) {
                if (sharedElements != null && sharedElements.isNotEmpty()) {
                    val searchIcon = sharedElements[1]
                    if (searchIcon.id != R.id.searchback) return
                    val centerX = (searchIcon.left + searchIcon.right) / 2
                    val hideResults = findTransition(
                        window.returnTransition as TransitionSet,
                        CircularReveal::class.java, R.id.results_container
                    ) as CircularReveal?
                    hideResults?.setCenter(Point(centerX, 0))
                }
            }
        })
    }

    fun findTransition(
        set: TransitionSet,
        clazz: Class<out Transition?>,
        @IdRes targetId: Int
    ): Transition? {
        for (i in 0 until set.transitionCount) {
            val transition = set.getTransitionAt(i)
            if (transition.javaClass == clazz) {
                if (transition.targetIds.contains(targetId)) {
                    return transition
                }
            }
            if (transition is TransitionSet) {
                val child = findTransition(transition, clazz, targetId)
                if (child != null) return child
            }
        }
        return null
    }

}
