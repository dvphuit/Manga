package dvp.app.azmanga.utils

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

/**
 * @author PhuDV
 * Created 8/7/21 at 6:33 PM
 */

@Composable
fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx() }

@Composable
fun Dp.toIntPx() = this.toPx().toInt()


val Float.dp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)
val Int.sp: Float
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity)

@Composable
fun Float.px2dp() = with(LocalDensity.current) { Dp(this@px2dp / this.density) }

@Composable
fun Float.dp() = with(LocalDensity.current) { Dp(this@dp).toSp() }