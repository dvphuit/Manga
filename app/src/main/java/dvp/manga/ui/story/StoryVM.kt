package dvp.manga.ui.story

import android.app.Application
import dvp.manga.data.model.Chapter
import dvp.manga.data.repository.ChapContentRepository
import dvp.manga.ui.BaseViewModel

class StoryVM(app: Application, repo: ChapContentRepository, chap: Chapter) : BaseViewModel(app) {
    val contents = repo.getChapContents(chap.id, chap.href!!)
}
