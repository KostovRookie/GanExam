package bg.gan.composeexam.userInterface.searchLottieRandom

import bg.gan.composeexam.model.remoteData.GetUserRepositoriesResponseItem
import bg.gan.composeexam.model.remoteData.GetUserResponse
import bg.gan.composeexam.model.repoAndUserStates.UserUiState
import bg.gan.composeexam.navGraph.DashDestinations
import bg.gan.composeexam.userInterface.theme.lightblack
import bg.gan.composeexam.userInterface.theme.lightbox
import bg.gan.composeexam.userInterface.theme.taupe100
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import bg.gan.composeexam.R
import bg.gan.composeexam.utilities.UserEvent
import bg.gan.composeexam.viewModel.UserViewModel
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.flowlayout.FlowRow


@Composable
fun SearchField(
    state: UserUiState,
    userViewModel: UserViewModel,
){
    val localFocusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(top = 20.dp),


        ) { TextField(
        value = state.searchString,
        shape = RoundedCornerShape(2.dp),
        label = { Text("Въведи потребител") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (state.searchString.isBlank()) {  //error if search field is empty
                    userViewModel.onEvent(
                        UserEvent.SetValidationError(
                            "Нека да не е празно"
                        )
                    )
                } else {
                    userViewModel.onEvent(UserEvent.SearchUser(state.searchString))
                    userViewModel.onEvent(UserEvent.SetSearchText(state.searchString))
                }
                localFocusManager.clearFocus()
            }
        ),
        onValueChange = {
            // users can't search the string that has more than 100 chars
            if (it.length <= 100) {
                userViewModel.onEvent(UserEvent.SetSearchText(it))
            } },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = lightblack,
            )
        },
        trailingIcon = {
            if (state.searchString.isNotEmpty()) {
                IconButton(
                    onClick = {
                        userViewModel.onEvent(
                            UserEvent.ClearSearchText
                        )
                        localFocusManager.clearFocus()
                    }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null
                    )
                }
            }
        },
        placeholder = {
            Text(text = "Търси потребител")
        },
        modifier = Modifier
            .weight(0.85f),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = lightbox,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ))
    }
}

@Composable
fun LoadingLottieAnimation(){ //json animations with low load
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = Int.MAX_VALUE, // infinite loop
        isPlaying = true
    )
    Surface(
        color = taupe100,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.height(400.dp).width(400.dp)
            )

        }
    }
}

@Composable
fun ErrorLottieAnimation(){ //same
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = Int.MAX_VALUE,
        isPlaying = true
    )
    Surface(
        color = taupe100,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.height(400.dp).width(400.dp)
            )

        }
    }
}

@Composable
fun BookmarkLottieAnimation(){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bookmark))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = Int.MAX_VALUE,
        isPlaying = true
    )
    Surface(
        color = taupe100,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.height(300.dp)
            )

        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileDetailsScreen(
    userResponse: GetUserResponse,
    navController: NavController
) {

    // Card of specific user, used in bookmark screen and main screen. Shows basic info without repos
    Card(
        modifier = Modifier
            .size(
                width = 400.dp,
                height = 450.dp,
            )
            .padding(15.dp),
        shape = RoundedCornerShape(3.dp),
        elevation = 4.dp,
        onClick = {
            navController.navigate(
                DashDestinations
                .UserDetail
                .createRoute(userResponse.login?:""))
            // go to specific use profile using roite login - the path to the profile in api
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.Start
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
                    contentDescription = "Картинка"
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
                            text = userResponse.name ?: "няма запазено име",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 1.dp)
                        )
                        Text(
                            text = userResponse.login ?: "няма login",
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
                Icon(Icons.Outlined.People, contentDescription = stringResource(R.string.company))
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = (userResponse.followers.toString() + stringResource(R.string.followers)),
                    maxLines = 1,
                    fontWeight = FontWeight.W500,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = (". " + userResponse.following.toString() + stringResource(R.string.follows)),
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
                    text = userResponse.company ?: "няма компания",
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
                    text = userResponse.location ?: "локация",
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
                    text = userResponse.email ?: "няма имейл",
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
                    text = "@${userResponse.twitter_username ?: stringResource(R.string.not_available)}",
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
                Icon(Icons.Outlined.Code, contentDescription = "иконка")
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = (userResponse.public_repos.toString() + stringResource(R.string.repos)),
                    maxLines = 1,
                    fontWeight = FontWeight.W500,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
                )
                Spacer(modifier = Modifier.width(35.dp))
                Text(
                    text = (userResponse.public_gists.toString() + " бързи връзки"),
                    maxLines = 1,
                    fontWeight = FontWeight.W500,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RepositoryCard( // below repository tab
    // should show column list of the repositories
    // problems with json serialization, working with moshi for now...
    repositoriesResponseItem: GetUserRepositoriesResponseItem,
) {
    var expanded by remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded) 50.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )
    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = 2.dp
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                repositoriesResponseItem.name?.let {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = it,
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        ))
                }
                if (expanded){
                    Spacer(modifier = Modifier.height(8.dp))
                    // description that wraps its content
                    Text(
                        text = repositoriesResponseItem.description ?: "няма",
                        style = MaterialTheme.typography.body2,
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
                            contentDescription = "харесани от други")
                        Spacer(modifier = Modifier.width(7.dp))
                        Text(
                            text =  repositoriesResponseItem.stargazers_count.toString(),
                            maxLines = 1,
                            fontWeight = FontWeight.W500,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_share),
                            contentDescription = "иконка")
                        Spacer(modifier = Modifier.width(7.dp))
                        Text(
                            text =  repositoriesResponseItem.forks.toString(),
                            maxLines = 1,
                            fontWeight = FontWeight.W500,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Icon(
                            Icons.Filled.Visibility,
                            contentDescription = "наблюдавани")
                        Spacer(modifier = Modifier.width(7.dp))
                        Text(
                            text =  repositoriesResponseItem.watchers_count.toString(),
                            maxLines = 1,
                            fontWeight = FontWeight.W500,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = TextUnit(value = 14F, type = TextUnitType.Sp),
                        )
                    }

                    FlowRow(
                        mainAxisSpacing = 5.dp,

                        ) {
                        for (chip in repositoriesResponseItem.topics!!) {
                            Chip(
                                onClick = {
                                    /*
     maybe i can try to use webview to open specific repo and view code with browser intent
                                   */


                                },
                                colors = ChipDefaults.chipColors(
                                    backgroundColor = Color(0xff9ed1e1),
                                    contentColor = Color.Black
                                )
                            ) {
                                Text(chip)
                            }
                        }
                    }
                }
            }
            IconButton(onClick = { expanded = !expanded }
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        "скрий"
                    } else {
                        "покажи"
                    }
                )
            }
        }
    }
}

@Composable
fun CustomTopBar(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    title: String? = null,
    pressBack: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        IconButtonMenu(icon = Icons.Rounded.ArrowBackIosNew, onClick = { pressBack() })
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
            )
        }
        icon?.let { IconButtonMenu(icon = it, onClick = onClick ) }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IconButtonMenu(icon: ImageVector, onClick: () -> Unit= {}) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.surface
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(44.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


@Preview
@Composable
fun ProfileDetailsScreenPreview() {
    ProfileDetailsScreen(
        userResponse = GetUserResponse(
            avatar_url = "",
            bio = "bio",
            blog = "https://",
            company = "",
            created_at = "2020-01-01T00:00:00Z",
            email = null,
            events_url = "https://api.github.com/users/RookieKostov/events{/privacy}",
            followers = 0,
            followers_url = "https://api.github.com/users/RookieKostov/followers",
            following = 0,
            following_url = "https://api.github.com/users/RookieKostov/following{/other_user}",
            gists_url = "https://api.github.com/users/RookieKostov/gists{/gist_id}",
            gravatar_id = "",
            hireable = true,
            html_url = "",
            id = 0,
            location = "Plovdiv",
            login = "kostov",
            name = "Georgi Kostov",
            node_id = "",
            organizations_url = "https://api.github.com/users/RookieKostov/orgs",
            public_gists = 0,
            public_repos = 0,
            received_events_url = "https://api.github.com/users/RookieKostov/received_events",
            repos_url = "https://api.github.com/users/RookieKostov/repos",
            site_admin = true,
            starred_url = "https://api.github.com/users/RookieKostov/starred{/owner}{/repo}",
            subscriptions_url = "https://api.github.com/users/RookieKostov/subscriptions",
            twitter_username = "",
            type = "User",
            updated_at = "2025-01-01T00:00:00Z",
            url = "https://api.github.com/users/RookieKostov"),
        navController = rememberNavController()
    )
}



