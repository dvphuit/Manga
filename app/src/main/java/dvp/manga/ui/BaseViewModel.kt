package dvp.manga.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {
    val state = MutableLiveData<ViewState>()

    internal fun launch(hasData: suspend () -> Boolean) = viewModelScope.launch {
        try {
            state.value = ViewState.LOADING
            state.value = if (hasData()) ViewState.SUCCESS else ViewState.EMPTY
        } catch (e: Exception) {
            state.value = ViewState.ERROR
            Log.e(this.javaClass.simpleName, e.localizedMessage!!)
        }
    }


}
