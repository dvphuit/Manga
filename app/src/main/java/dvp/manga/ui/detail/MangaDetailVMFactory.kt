package dvp.manga.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.ChapterRepository
import dvp.manga.data.repository.MangaRepository

/**
 * @author dvphu on 19,March,2020
 */

class MangaDetailVMFactory(private val mangaRepo: MangaRepository, private val chapRepo: ChapterRepository, private val manga: Manga) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MangaDetailVM(mangaRepo, chapRepo, manga) as T
    }
}