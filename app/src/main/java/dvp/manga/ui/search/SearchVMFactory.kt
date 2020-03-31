package dvp.manga.ui.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dvp.manga.data.repository.MangaRepository

/**
 * @author dvphu on 18,March,2020
 */

class SearchVMFactory(private val app: Application, private val repository: MangaRepository): ViewModelProvider.AndroidViewModelFactory(app){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel2(app, repository) as T
    }
}