package dvp.manga.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.ChapterRepository

/**
 * @author dvphu on 19,March,2020
 */

class MangaDetailVMFactory(private val app: Application, private val repo: ChapterRepository, private val manga: Manga)
    : ViewModelProvider.AndroidViewModelFactory(app) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MangaDetailVM(app, repo, manga) as T
    }
}