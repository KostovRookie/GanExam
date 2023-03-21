package bg.gan.composeexam.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bg.gan.composeexam.model.repoAndUserStates.UserUiState
import bg.gan.composeexam.utilities.UserEvent
import bg.gan.composeexam.viewModel.useCase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userUseCase: GetUserUseCase): ViewModel() {

    private val _state = mutableStateOf(UserUiState())
    val state: State<UserUiState> = _state
    private var searchJob: Job? = null
    private var lastSearch = ""

    init {
        searchUser("")
    }

    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.SearchUser -> {
                viewModelScope.launch {
                    if (event.search.isNotBlank()){
                        searchUser(event.search)
                    }
                }
            }
            is UserEvent.ClearSearchText -> {
                _state.value = _state.value.copy(
                    searchString = ""
                )
            }
            is UserEvent.SetSearchText -> {
                _state.value = _state.value.copy(
                    searchString = event.search
                )
            }
            else -> {}
        }
    }

    private fun searchUser(search: String) {
        // Stop calls for search
        if (lastSearch == search) return
        // Cancel if it is not completed
        searchJob?.cancel()
        // Create search Job
        searchJob = viewModelScope.launch {
            when (val result = userUseCase.invoke(search)) {
                is bg.gan.composeexam.utilities.State.Success -> {
                    _state.value = UserUiState(false, result.data)
                }
                is bg.gan.composeexam.utilities.State.Failure -> {
                    _state.value = UserUiState(true, null, true)
                }
            }
        }
    }
    private val _userResponse: MutableStateFlow<UserUiState> = MutableStateFlow(UserUiState(true, null, false))
    val user get() = _userResponse

    fun getUser(username: String) {
        viewModelScope.launch {
            _userResponse.emit(UserUiState(true, null, false))
            when (val response = userUseCase.invoke(username)) {
                is bg.gan.composeexam.utilities.State.Success -> {
                    _userResponse.emit(UserUiState(false, response.data, false))
                }
                is bg.gan.composeexam.utilities.State.Failure -> {
                    _userResponse.emit(UserUiState(false, null, true))
                }
            }
        }
    }
}