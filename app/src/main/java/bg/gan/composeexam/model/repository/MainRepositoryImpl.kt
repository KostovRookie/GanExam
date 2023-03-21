package bg.gan.composeexam.model.repository

import bg.gan.composeexam.model.apiService.ApiService
import bg.gan.composeexam.model.remoteData.GetUserRepositoriesResponse
import bg.gan.composeexam.model.remoteData.GetUserResponse
import bg.gan.composeexam.model.repository.BaseRepository
import bg.gan.composeexam.model.repository.MainRepository

import com.elders.eldersjunior.utilities.State
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService
    )
    : MainRepository, BaseRepository() {
    override suspend fun getUser(user: String): State<GetUserResponse> {
        return safeApiCall {
            apiService.getUser(user)
        }
    }

    override suspend fun getUserRepos(user: String): State<GetUserRepositoriesResponse> {
        return safeApiCall {
            apiService.getUserRepositories(user)
        }
    }

}