package dvp.app.azmanga.base

sealed class Resource<T>() {
    class Empty<T>() : Resource<T>()
    class Loading<T>(data: T? = null) : Resource<T>()
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val throwable: Throwable, val data: T?) : Resource<T>()
}