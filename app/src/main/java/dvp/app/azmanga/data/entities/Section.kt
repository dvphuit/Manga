package dvp.app.azmanga.data.entities

import android.os.Parcelable
import androidx.lifecycle.LiveData
import dvp.app.azmanga.base.Resource

abstract class Section

data class Top(val mangaList: LiveData<Resource<List<Manga>>>) : Section()

data class MangaSection(
    var section: SectionRoute, val mangaList: LiveData<Resource<List<Manga>>>,
    val viewState: Parcelable?
) : Section()

data class SectionDetail(var section: SectionRoute, var mangaList: List<Manga>)

enum class SectionRoute(val value: String) {
    LAST_UPDATE("Last Update"),
    FAVOURITE("Favourite"),
    NEW_MANGA("New Manga"),
    FOR_BOY("For Boy"),
    FOR_GIRL("For Girl"),
    SEARCH("Search"),
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    DETECTIVE("Detective"),
    DRAMA("Drama"),
    FANTASY("Fantasy"),
    ISEKAI("Isekai"),
    MAGIC("Magic"),
    PSYCHOLOGICAL("Psychological"),
    SHOUNEN("Shounen"),
    SPORT("Sports"),
    SUPER_NATURAL("Supernatural"),
    WEBTOON("Webtoon"),
}