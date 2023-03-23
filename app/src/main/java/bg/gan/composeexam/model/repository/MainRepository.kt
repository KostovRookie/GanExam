package bg.gan.composeexam.model.repository

import bg.gan.composeexam.model.remoteData.GetUserRepositoriesResponse
import bg.gan.composeexam.model.remoteData.GetUserResponse
import bg.gan.composeexam.utilities.State


interface MainRepository {
    // interface to work with ue case for simplicity and cleaner code

    suspend fun getUser(user: String): State<GetUserResponse>

    suspend fun getUserRepos(user: String): State<GetUserRepositoriesResponse>


}