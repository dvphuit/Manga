package dvp.manga.data.remote

import androidx.lifecycle.LiveData
import dvp.manga.data.model.Manga

/**
 * @author dvphu on 10,March,2020
 */

abstract class BaseCrawler {
    abstract fun getMangas(): LiveData<List<Manga>>
}