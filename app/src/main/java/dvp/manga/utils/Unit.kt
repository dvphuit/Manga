package dvp.manga.utils

import android.content.res.Resources

val Int.dp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()