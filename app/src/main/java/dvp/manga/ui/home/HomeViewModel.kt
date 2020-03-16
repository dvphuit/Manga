package dvp.manga.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dvp.manga.data.model.Manga
import dvp.manga.data.repository.MangaRepository

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    private val mangaRepository = MangaRepository(app)
    private val mangas = mangaRepository.getMangas()

    fun getMangas(): LiveData<List<Manga>> {
        return mangas
    }
}
