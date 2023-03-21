package bg.gan.composeexam.model.repository

import bg.gan.composeexam.utilities.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

open class BaseRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): State<T> {
       return withContext(Dispatchers.IO){
           try{
               State.Success(apiCall.invoke())
           } catch (throwable: Throwable){
               when(throwable){
                   is HttpException -> { State.Failure(
                       false,
                       throwable.code(),
                       throwable.response()?.errorBody()
                   )}
                   else -> { State.Failure(false, null, null)}
               }
           }
       }
    }
}