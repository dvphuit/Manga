package dvp.app.azmanga.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

/**
 * @author PhuDV
 * Created 8/14/21
 */

@Composable
fun BottomBar(viewModel: AppViewModel, nav: NavHostController) {
    val currentRoute by viewModel.selectedTab

    BottomNavigation(
        backgroundColor = Color.Red,
        contentColor = Color.White
    ) {
        menuBar.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.7f),
                alwaysShowLabel = true,
                selected = item.route == currentRoute.route,
                onClick = {
                    viewModel.selectTab(item)
                    nav.navigate(item.route) {
                        nav.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}