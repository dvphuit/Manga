package dvp.app.azmanga.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dvp.app.azmanga.LocalBackPressedDispatcher
import dvp.app.azmanga.navigation.MainGraph
import dvp.app.azmanga.ui.theme.AZMangaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
            }
            CompositionLocalProvider(LocalBackPressedDispatcher provides this.onBackPressedDispatcher) {
                ProvideWindowInsets() {
                    AZMangaTheme {
                        MainGraph()
                    }
                }
            }
        }
    }

}

