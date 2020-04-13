package dvp.manga.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.ChapterRepository

/**
 * @author dvphu on 19,March,2020
 */

class MangaDetailVMFactory(private val repo: ChapterRepository, private val manga: Manga)
    : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MangaDetailVM(repo, manga) as T
    }
}