package dvp.manga.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dvp.manga.data.repository.MangaRepository

/**
 * @author dvphu on 18,March,2020
 */

class HomeVMFactory(private val repository: MangaRepository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}