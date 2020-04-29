package dvp.manga.utils

import android.content.res.Resources


inline val Int.px: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

inline val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


inline val String?.hash: Int
    get() = if (this != null) this.hashCode() * 31 else 0