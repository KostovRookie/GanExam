package bg.gan.composeexam.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bg.gan.composeexam.model.repoAndUserStates.RepoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRepositoryViewModel @Inject constructor(private val getRepoUseCase: GetRepoUseCase): ViewModel() {
    private val _repositoryResponse: MutableStateFlow<RepoUiState> = MutableStateFlow(RepoUiState(true, null, false))
    val repositories get() = _repositoryResponse

    fun getRepositories(username: String) {
        viewModelScope.launch {
            _repositoryResponse.emit(RepoUiState(true, null, false))
            when (val response = getRepoUseCase.invoke(username)) {
                is State.Success -> {
                    _repositoryResponse.emit(RepoUiState(false, response.data, false))
                }
                is State.Failure -> {
                    _repositoryResponse.emit(RepoUiState(false, null, true))
                }
            }
        }
    }
}