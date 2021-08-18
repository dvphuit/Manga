package dvp.app.azmanga.navigation

import androidx.annotation.MainThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * @author PhuDV
 * Created 8/8/21 at 1:21 AM
 */

class AppViewModel : ViewModel() {
    private val _selectedTab: MutableState<Screen> = mutableStateOf(Screen.Home)
    val selectedTab: State<Screen> get() = _selectedTab

    @MainThread
    fun selectTab(screen: Screen) {
        _selectedTab.value = screen
    }

}