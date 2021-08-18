package dvp.app.azmanga.utils

import android.os.Build
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

/**
 * @author PhuDV
 * Created 8/15/21
 */


@Stable
fun Color.asNativeColor(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        android.graphics.Color.argb(alpha, red, green, blue)
    } else {
        TODO("VERSION.SDK_INT < O")
    }
}