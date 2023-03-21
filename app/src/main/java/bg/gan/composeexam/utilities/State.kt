package bg.gan.composeexam.utilities

import okhttp3.ResponseBody

sealed class State <out T> {
    //@Serializable
    data class Success<out T>(val data: T) : State<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : State<Nothing>()
}