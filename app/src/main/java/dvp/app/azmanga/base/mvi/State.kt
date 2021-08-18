package dvp.app.azmanga.base.mvi


interface UiState

interface UiEvent

interface UiEffect

sealed class DataState<T> {

    data class Response<T>(
        val uiComponent: UIComponent
    ): DataState<T>()

    data class Data<T>(
        val data: T
    ): DataState<T>()


}
sealed class UIComponent{

    data class Dialog(
        val title: String,
        val description: String,
    ): UIComponent()
    data class None(
        val message: String,
    ): UIComponent()
}
