package bg.gan.composeexam.userInterface.layouts


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import bg.gan.composeexam.R
import bg.gan.composeexam.model.remoteData.GetUserResponse
import bg.gan.composeexam.userInterface.searchLottieRandom.CustomTopBar
import bg.gan.composeexam.userInterface.searchLottieRandom.RepositoryCard
import bg.gan.composeexam.userInterface.theme.taupe100
import bg.gan.composeexam.viewModel.DatabaseViewModel
import bg.gan.composeexam.viewModel.UserRepositoryViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch


@Composable
fun UserDetailsScreen(
    userResponse: GetUserResponse,
    databaseViewModel: DatabaseViewModel = hiltViewModel(),

    navBack: () -> Unit
){
    val bookmarked = databaseViewModel.isBookmarked(userResponse.login ?: "").observeAsState(false)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = { // top bar located in user card
            CustomTopBar(
                title = userResponse.login,
                pressBack = navBack,
                // change states
                icon = Icons.Rounded.Bookmark,
                onClick = {
                    coroutineScope.launch {
                        if (bookmarked.value) {
                            databaseViewModel.deleteBookmark(userResponse, coroutineScope)
                            Toast.makeText(context, "Премахнат от база данни", Toast.LENGTH_SHORT).show()
                        } else {
                            databaseViewModel.addBookmark(userResponse, coroutineScope)
                            Toast.makeText(context, "Добавен към база данни", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        },
        backgroundColor = taupe100,
        content = { contentPadding ->
            Details(modifier =Modifier.padding(contentPadding),
                userResponse = userResponse

            )

        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Details(
    userResponse: GetUserResponse,
    userRepoViewModel: UserRepositoryViewModel = hiltViewModel(),
    modifier: Modifier
) {
    val tabs = listOf(
        PagerItem.Repositories
    )
    val pagerState = rememberPagerState() // why error????
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(CircleShape),
                painter = rememberAsyncImagePainter(userResponse.avatar_url),
                contentScale = ContentScale.Fit,
                contentDescription = "Картинка потребител"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = userResponse.name ?: "няма",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 1.dp)
                    )
                    Text(
                        text = userResponse.login ?: "няма",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = userResponse.bio ?: "няма",
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
            modifier = Modifier.padding(5.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.People, contentDescription = "иконка")
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = (userResponse.followers.toString() + " последователи"),
                maxLines = 1,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = (". " + userResponse.following.toString() + " последвани"),
                maxLines = 1,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.AccountBox, contentDescription = "иконка")
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = userResponse.company ?: "няма",
                maxLines = 1,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.LocationOn, contentDescription = "иконка")
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = userResponse.location ?: "няма",
                maxLines = 1,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.Email, contentDescription = "иконка")
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = userResponse.email ?: "няма",
                maxLines = 1,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.Web, contentDescription = "иконка")
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = "@${userResponse.twitter_username ?: "няма"}",
                maxLines = 1,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.Code, contentDescription = stringResource(R.string.icon))
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = (userResponse.public_repos.toString() + stringResource(R.string.repositories)),
                maxLines = 1,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
            )
            Spacer(modifier = Modifier.width(35.dp))
            Text(
                text = (userResponse.public_gists.toString() + stringResource(R.string.gists)),
                maxLines = 1,
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
            )
        }
        Tabs(tabs = tabs, pagerState = pagerState)
        TabContent(pagerState = pagerState, userRepoViewModel, userResponse.login ?: "")
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent( //content repository tab to show them in a column
    pagerState: PagerState,
    userRepoViewModel: UserRepositoryViewModel,
    username: String
) {

    val repositories = remember{
        userRepoViewModel.getRepositories(username)
        userRepoViewModel.repositories
    }.collectAsState()

    HorizontalPager(state = pagerState, count = 1) { page ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
//
            if (page == 0){
                LazyColumn {
                    when {
                        repositories.value.isLoading -> {

                        }
                        repositories.value.data != null -> {
                            items(repositories.value.data!!.size) { repository ->
                                repositories.value.data?.get(repository)?.let {
                                    RepositoryCard(it)
                                }
                            }
                        }
                        repositories.value.error -> {
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<PagerItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                color = Color.Black,
            )
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = { Text(text = tab.title ,maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = TextUnit(value = 12F, type = TextUnitType.Sp)) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@Preview
@Composable
fun PreviewDetailScreen(){
    UserDetailsScreen(
        userResponse = GetUserResponse(
            avatar_url = "https://avatars.githubusercontent.com/u/",
            bio = "Smart, Pretty, Great at JAVA , Kotlin and honest 50% of the time",
            blog = "Smart, Pretty, Great at JAVA , Kotlin and honest 50% of the time",
            company = "",
            created_at = "2022-10-29T11:35:29Z",
            email = null,
            events_url = "https://api.github.com/users/KostovRookie/events{/privacy}",
            followers = 0,
            followers_url = "https://api.github.com/users/KostovRookie/followers",
            following = 0,
            following_url = "https://api.github.com/users/KostovRookie/following{/other_user}",
            gists_url = "https://api.github.com/users/KostovRookie/gists{/gist_id}",
            gravatar_id = "",
            hireable = true,
            html_url = "",
            id = 0,
            location = "Plovdiv",
            login = "KostovRookie",
            name = "",
            node_id = "",
            organizations_url = "https://api.github.com/users/KostovRookie/orgs",
            public_gists = 60,
            public_repos = 100,
            received_events_url = "https://api.github.com/users/KostovRookie/received_events",
            repos_url = "https://api.github.com/users/KostovRookie/repos",
            site_admin = true,
            starred_url = "https://api.github.com/users/KostovRookie/starred{/owner}{/repo}",
            subscriptions_url = "https://api.github.com/users/KostovRookie/subscriptions",
            twitter_username = "",
            type = "User",
            updated_at = "2020-01-01T00:00:00Z",
            url = "https://api.github.com/users/KostovRookie")
    ) {}
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun TabPreview() {
    val tabs = listOf(

        PagerItem.Repositories
    )
    val pagerState = rememberPagerState()
    Tabs(tabs = tabs, pagerState = pagerState)
}

sealed class PagerItem(var title:String) {

    object Repositories: PagerItem("Проекти")
}