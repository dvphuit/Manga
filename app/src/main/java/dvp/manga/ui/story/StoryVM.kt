package dvp.manga.ui.story

import android.app.Application
import dvp.manga.data.model.ChapContent
import dvp.manga.data.model.Chapter
import dvp.manga.data.repository.ChapContentRepository
import dvp.manga.ui.BaseViewModel

class StoryVM(app: Application, private val repo: ChapContentRepository, private val chap: Chapter) : BaseViewModel(app) {
    lateinit var contents: List<ChapContent>

    init {
        getChapContent()
    }

    private fun getChapContent() {
        launch {
            contents = repo.getContents(chap.href!!)
            return@launch contents.isNotEmpty()
        }
    }
}
