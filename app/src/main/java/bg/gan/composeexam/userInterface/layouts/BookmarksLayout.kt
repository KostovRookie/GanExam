package bg.gan.composeexam.userInterface.layouts

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import bg.gan.composeexam.userInterface.searchLottieRandom.BookmarkLottieAnimation
import bg.gan.composeexam.userInterface.searchLottieRandom.CustomTopBar
import bg.gan.composeexam.userInterface.searchLottieRandom.ProfileDetailsScreen
import bg.gan.composeexam.viewModel.DatabaseViewModel
import kotlinx.coroutines.launch

@Composable
fun BookmarksScreen(
    modifier: Modifier = Modifier,
    databaseViewModel: DatabaseViewModel = hiltViewModel(),
    navController: NavController
) {
    val bookmarkList = databaseViewModel.bookmarks.observeAsState(initial = listOf())
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            CustomTopBar(
                icon = Icons.Filled.Delete,
                title = "Запазени",
                pressBack = {navController.navigateUp()},
                onClick = {
                    coroutineScope.launch {
                        if(bookmarkList.value.isNotEmpty()) {
                            databaseViewModel.deleteAll()
                            Toast.makeText(context, "База данни изтрита", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Няма какво да се изтрие", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        },
        content = { contentPadding ->
            run {
                if (bookmarkList.value.isEmpty()) {
                    // Loading screen
                    Column(
                        modifier = modifier.fillMaxSize().padding(contentPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BookmarkLottieAnimation()
                    }
                } else {
                    Column(Modifier.fillMaxSize()) {
                        LazyColumn(Modifier.fillMaxSize()) {
                            itemsIndexed(bookmarkList.value) { _, element ->
                                ProfileDetailsScreen(
                                    userResponse = element,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}