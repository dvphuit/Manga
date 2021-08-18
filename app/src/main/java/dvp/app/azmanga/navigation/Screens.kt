package dvp.app.azmanga.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import dvp.app.azmanga.R

/**
 * @author PhuDV
 * Created 8/1/21 at 3:37 PM
 */

val startRoute = Screen.Home.route

sealed class Screen(var route: String, var icon: ImageVector, var title: String) {
    object Splash : Screen("splash", Icons.Filled.Favorite, "Splash")
    object Home : Screen("home", Icons.Filled.Favorite, "Home")
    object Library : Screen("library", Icons.Filled.Favorite, "Library")
    object Setting : Screen("setting", Icons.Filled.Favorite, "Setting")
}

val menuBar = listOf(
    Screen.Home,
    Screen.Library,
    Screen.Setting,
)