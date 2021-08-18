package dvp.app.azmanga.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dvp.app.azmanga.R


val fontFamily = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.nunito_sans_bold,
            weight = FontWeight.Bold
        ),
        Font(
            resId = R.font.nunito_sans_semibold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resId = R.font.nunito_sans_regular,
            weight = FontWeight.W400
        ),
        Font(
            resId = R.font.nunito_sans_light,
            weight = FontWeight.W300,
        ),
        Font(
            resId = R.font.nunito_sans_elight,
            weight = FontWeight.W200,
        )
    )
)

val appTypography = Typography(defaultFontFamily = fontFamily)