package dvp.manga.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(app: Application) : AndroidViewModel(app){
    internal fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            Log.e(this.javaClass.simpleName, e.message!!)
        }
    }
}