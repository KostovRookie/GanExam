package bg.gan.composeexam.viewModel.useCase

import bg.gan.composeexam.model.remoteData.GetUserRepositoriesResponse
import bg.gan.composeexam.model.repository.MainRepository
import bg.gan.composeexam.utilities.State
import javax.inject.Inject

class GetRepositoryUseCase @Inject constructor(private val mainRepository: MainRepository) {
    suspend fun invoke(username: String): State<GetUserRepositoriesResponse> =
        mainRepository.getUserRepos(username)
}
//using use case to invoke search users or repositories only when needed