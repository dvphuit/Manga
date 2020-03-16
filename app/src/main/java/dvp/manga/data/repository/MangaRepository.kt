package dvp.manga.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import dvp.manga.data.model.Manga
import dvp.manga.data.remote.TruyenQQ

class MangaRepository(app: Application) {

    private val mangas: LiveData<List<Manga>> = TruyenQQ().getMangas()

    fun getMangas(): LiveData<List<Manga>> {
        return mangas
    }

}