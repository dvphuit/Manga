package dvp.manga.data.model

import androidx.lifecycle.LiveData

/**
 * @author dvphu on 24,April,2020
 */

abstract class Section

data class Top(val mangaList: LiveData<List<Manga>>) : Section()

data class MangaSection(var title: String = "abc", val mangaList: LiveData<List<Manga>>) : Section()