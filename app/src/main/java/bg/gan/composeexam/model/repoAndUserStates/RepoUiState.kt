package bg.gan.composeexam.model.repoAndUserStates

import bg.gan.composeexam.model.remoteData.GetUserRepositoriesResponse

//@Serializable
data class RepoUiState(
    val isLoading: Boolean = false,
    val data: GetUserRepositoriesResponse? = null,
    val error: Boolean = false
)