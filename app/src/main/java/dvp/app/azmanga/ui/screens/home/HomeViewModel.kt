package dvp.app.azmanga.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dvp.app.azmanga.base.Resource
import dvp.app.azmanga.base.ViewState
import dvp.app.azmanga.data.entities.Manga
import dvp.app.azmanga.data.entities.SectionRoute
import dvp.app.azmanga.data.repositories.manga.MangaRepoIml
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: MangaRepoIml
) : ViewModel() {

    private val _topMangas = MutableStateFlow<Resource<List<Manga>>>(Resource.Empty())
    val topMangas: StateFlow<Resource<List<Manga>>>
        get() = _topMangas.asStateFlow()

    private val _lastMangas = MutableStateFlow<Resource<List<Manga>>>(Resource.Empty())
    val lastMangas: StateFlow<Resource<List<Manga>>>
        get() = _topMangas.asStateFlow()

    init {
        getTopManga()
//        getLast()
    }

    private fun getTopManga() = viewModelScope.launch {
        repo.getTop().collect { _topMangas.value = it }
    }

    private fun getLast() = viewModelScope.launch {
        repo.getMangas(SectionRoute.LAST_UPDATE, 0).collect { _lastMangas.value = it }
    }

}
