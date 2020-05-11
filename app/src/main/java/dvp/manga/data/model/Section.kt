package dvp.manga.data.model

import android.os.Parcelable
import androidx.lifecycle.LiveData
import dvp.manga.ui.ResultData
import java.io.Serializable

/**
 * @author dvphu on 24,April,2020
 */

abstract class Section

data class Top(val mangaList: LiveData<ResultData<List<Manga>>>) : Section()

data class MangaSection(
    var section: SectionRoute, val mangaList: LiveData<ResultData<List<Manga>>>,
    val viewState: Parcelable?
) : Section()

data class SectionDetail(var section: SectionRoute, var mangaList: List<Manga>) : Serializable

enum class SectionRoute(val value: String) {
    LAST_UPDATE("Last Update"),
    FAVOURITE("Favourite"),
    NEW_MANGA("New Manga"),
    FOR_BOY("For Boy"),
    FOR_GIRL("For Girl")
}
