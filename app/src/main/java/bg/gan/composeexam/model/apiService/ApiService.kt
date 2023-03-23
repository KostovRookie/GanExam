package bg.gan.composeexam.model.apiService

import bg.gan.composeexam.model.remoteData.GetUserRepositoriesResponse
import bg.gan.composeexam.model.remoteData.GetUserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // get user data
    @GET("users/{user}")
    suspend fun getUser(
        @Path("user") user: String): GetUserResponse


    // Get users repositories
    @GET("users/{user}/repos")
    suspend fun getUserRepositories(
        @Path("user") user: String): GetUserRepositoriesResponse
}