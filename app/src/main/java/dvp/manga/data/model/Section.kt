package dvp.manga.data.model

import android.os.Parcelable
import androidx.lifecycle.LiveData
import java.io.Serializable

/**
 * @author dvphu on 24,April,2020
 */

abstract class Section

data class Top(val mangaList: LiveData<List<Manga>>) : Section()

data class MangaSection(var title: String = "abc", val mangaList: LiveData<List<Manga>>,val viewState: Parcelable?) : Section()

data class SectionDetail(var title: String, var mangaList: List<Manga>) : Serializable
