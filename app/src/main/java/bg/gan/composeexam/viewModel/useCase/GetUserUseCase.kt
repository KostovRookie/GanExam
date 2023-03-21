package bg.gan.composeexam.viewModel.useCase

import bg.gan.composeexam.model.remoteData.GetUserResponse
import bg.gan.composeexam.model.repository.MainRepository
import bg.gan.composeexam.utilities.State
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val mainRepository: MainRepository) {
    suspend fun invoke(user: String): State<GetUserResponse> =
        mainRepository.getUser(user)
}

//using use case to invoke search users or repositories only when needed