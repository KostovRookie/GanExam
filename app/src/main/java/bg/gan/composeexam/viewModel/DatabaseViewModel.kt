package bg.gan.composeexam.viewModel

import androidx.lifecycle.ViewModel
import bg.gan.composeexam.model.remoteData.GetUserResponse
import bg.gan.composeexam.model.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(private val roomRepository: RoomRepository): ViewModel() {

    fun getBookmark(id:  Int) = roomRepository.getLocalBookmark(id)
    val bookmarks = roomRepository.getBookmarks

    fun isBookmarked(login: String) = roomRepository.checkBookmarked(login)

    fun deleteBookmark(userResponse: GetUserResponse, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            roomRepository.deleteBookmark(userResponse.login ?: "")
        }
    }
    fun addBookmark(userResponse: GetUserResponse, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            roomRepository.addBookmark(userResponse.toBookmarks())
        }
    }

    suspend fun deleteAll() = roomRepository.deleteAll()

}