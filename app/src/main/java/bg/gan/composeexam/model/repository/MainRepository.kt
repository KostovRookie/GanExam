package bg.gan.composeexam.model.repository

import com.elders.eldersjunior.data.remoteData.GetUserRepositoriesResponse
import com.elders.eldersjunior.data.remoteData.GetUserResponse
import com.elders.eldersjunior.utilities.State

interface MainRepository {

    suspend fun getUser(user: String): State<GetUserResponse>

    suspend fun getUserRepos(user: String): State<GetUserRepositoriesResponse>


}