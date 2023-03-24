package bg.gan.composeexam.model.repoAndUserStates


import bg.gan.composeexam.model.remoteData.GetUserResponse
import kotlinx.serialization.Serializable

//@Serializable
data class UserUiState(
    val isLoading: Boolean = false,
    val data: GetUserResponse? = null,
    val error: Boolean = false,
    val searchString : String = ""
)