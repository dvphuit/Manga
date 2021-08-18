package dvp.app.azmanga.data.mapper

//
//import dvp.app.azmanga.data.entities.Manga
//import dvp.app.azmanga.base.EntityMapper
//import dvp.app.azmanga.data.remote.models.MangaItem
//
//import javax.inject.Inject
//
//class MangaMapper @Inject constructor() : EntityMapper<MangaItem, Manga> {
//
//    override fun toDomain(entity: MangaItem) = Manga()
//
//    override fun toEntity(domainModel: Manga) = MangaItem()
//
//    override fun toDomainList(entities: List<MangaItem>) = entities.map { toDomain(it) }
//
//    override fun toEntityList(domains: List<Manga>) = domains.map { toEntity(it) }
//
//}