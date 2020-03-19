package dvp.manga.ui.common

import dvp.manga.data.model.Manga

class MangaVM(manga: Manga) {
    val name: String = manga.name ?: ""
    val cover: String = manga.cover ?: ""
    val last_chap: String = manga.last_chap ?: ""
}