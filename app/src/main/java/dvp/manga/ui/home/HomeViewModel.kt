package dvp.manga.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MangaRepository) : ViewModel() {

    var mangas = MutableLiveData<List<Manga>>()

    init {
        getMangas()
    }

    private fun getMangas() {
        launch({
            mangas.value = repository.getMangas()
        }, {
            Log.e(this.javaClass.simpleName, it.message!!)
        })
    }


    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) =
        viewModelScope.launch {
            try {
                block()
            } catch (e: Throwable) {
                error(e)
            }
        }
}
