package dvp.manga.ui.section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dvp.manga.data.repository.MangaRepository

/**
 * @author dvphu on 18,March,2020
 */

class SectionVMFactory(private val repository: MangaRepository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SectionViewModel(repository) as T
    }
}