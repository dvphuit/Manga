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
import dvp.manga.data.model.Manga
import dvp.manga.databinding.ActivitySearchBinding
import dvp.manga.ui.adapter.MangaAdapter
import dvp.manga.utils.Injector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch


class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModels {
        Injector.getSearchVMFactory(this)
    }

    private lateinit var adapter: MangaAdapter


    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)) {
            adapter = MangaAdapter(mangaList)
            mangaList.adapter = adapter
            subscribeUi(adapter)
            searchView.doAfterTextChanged { query ->
                adapter.setLazyCallback { page ->
                    sendQuery(query.toString(), page)
                }
            }
        }
        setupTransitions()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun sendQuery(query: String, page: Int) {
        lifecycleScope.launch {
            viewModel.query.send(QueryModel(query, page))
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun subscribeUi(adapter: MangaAdapter) {
        viewModel.state.observe(this) {
            when (it) {
                is Result.Success -> {
                    @Suppress("UNCHECKED_CAST")
                    adapter.submitData(it.data as List<Manga>)
                }
                is Result.Error -> {
                    Toast.makeText(this, it.errMsg, Toast.LENGTH_SHORT).show()
                }
                is Result.EmptyQuery -> {
                    Toast.makeText(this, "Must be over 3 characters", Toast.LENGTH_SHORT).show()
                    adapter.submitData(emptyList())
                }
            }
        }
    }

    private fun setupTransitions() {
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
