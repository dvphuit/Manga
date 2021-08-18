package dvp.app.azmanga.data.repositories.manga

import dvp.app.azmanga.base.Resource
import dvp.app.azmanga.data.entities.Manga
import dvp.app.azmanga.data.entities.SectionRoute
import kotlinx.coroutines.flow.Flow

interface MangaRepo {

    fun getTop(): Flow<Resource<out List<Manga>>>

    fun getMangas(section: SectionRoute, page: Int): Flow<Resource<out List<Manga>>>

}
