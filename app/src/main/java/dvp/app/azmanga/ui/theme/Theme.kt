package dvp.app.azmanga.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dvp.app.azmanga.R

val lightThemeColors = lightColors(
    primary = Color(98, 0, 238),
    primaryVariant = Color(55, 0, 179),
    onPrimary = Color(18, 7, 9),
    secondary = Color(0, 150, 136),
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(150, 0, 20),
    onError = Color.White
)

val darkThemeColors = darkColors(
    primary = Color(98, 0, 238),
    primaryVariant = Color(55, 0, 179),
    onPrimary = Color(212, 212, 195),
    secondary = Color(0, 150, 136),
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(150, 0, 20),
    onError = Color.White
)

@Composable
fun AZMangaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        darkThemeColors
    } else {
        lightThemeColors
    }

    MaterialTheme(
        colors = colors,
        typography = appTypography,
        shapes = Shapes,
        content = content
    )
}