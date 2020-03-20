package dvp.manga.ui.story

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dvp.manga.data.model.Chapter
import dvp.manga.data.repository.ChapContentRepository

/**
 * @author dvphu on 20,March,2020
 */

class StoryVMFactory(private val app: Application, private val repo: ChapContentRepository, private val chap: Chapter)
    : ViewModelProvider.AndroidViewModelFactory(app) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StoryVM(app, repo, chap) as T
    }
}