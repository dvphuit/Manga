package dvp.manga.utils

import android.content.res.Resources


inline val Int.px: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

inline val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()