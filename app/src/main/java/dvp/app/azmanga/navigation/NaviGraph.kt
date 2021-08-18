package dvp.app.azmanga.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import dvp.app.azmanga.ui.components.TopBar
import dvp.app.azmanga.ui.screens.home.HomeScreen
import dvp.app.azmanga.ui.screens.library.LibraryScreen
import dvp.app.azmanga.ui.screens.setting.SettingScreen


@Composable
fun MainGraph() {
    val navController = rememberNavController()
    val viewModel = AppViewModel()
    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        topBar = {
            TopBar(viewModel = viewModel)
        },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                SideBar(viewModel = viewModel, nav = navController)
                NavHost(navController = navController, startDestination = startRoute) {
                    composable(Screen.Home.route) {
                        HomeScreen(viewModel = hiltViewModel())
                    }

                    composable(Screen.Library.route) {
                        LibraryScreen()
                    }

                    composable(Screen.Setting.route) {
                        SettingScreen()
                    }
                }
            }
        }
    )

}



