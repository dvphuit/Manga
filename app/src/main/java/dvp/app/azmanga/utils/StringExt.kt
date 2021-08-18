package dvp.app.azmanga.utils

/**
 * @author PhuDV
 * Created 8/1/21
 */

inline val String?.hash: Int
 get() = if (this != null) this.hashCode() * 31 else 0

inline val String.number: String
 get() = Regex("(\\d+\\.\\d+)|\\d+").find(this)?.value ?: "?"

inline val String.numberF: Float
 get() = Regex("(\\d+\\.\\d+)|\\d+").find(this)?.value?.toFloat() ?: 0f