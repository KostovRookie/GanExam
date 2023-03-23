package bg.gan.composeexam.navGraph

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import bg.gan.composeexam.userInterface.layouts.BookmarksScreen
import bg.gan.composeexam.userInterface.layouts.HomeScreen

//modifying bottom app par icons and behaviour
enum class HomeTabs(
    val title: String,
    val icon: ImageVector,
    val route: String
){
    HOME("Начало", Icons.Rounded.Search, "tabs/home"),
    BOOKMARK("Запазени потребители", Icons.Rounded.Star, "tabs/bookmark"),
}

fun NavGraphBuilder.addHomeGraph(
    navController: NavController,
    modifier: Modifier = Modifier
){
    composable(HomeTabs.HOME.route){
        HomeScreen(
            modifier = modifier,
            navController = navController,
        )
    }
    composable(HomeTabs.BOOKMARK.route){
        BookmarksScreen(navController = navController)
    }
}

@Composable
fun GitAppBottomBar(
    navController: NavController,
    tabs: Array<HomeTabs>
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: HomeTabs.HOME.route

    val routes = remember {
        HomeTabs.values().map { it.route }
    }
    if (currentRoute in routes){
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface
        ) {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = tab.icon, contentDescription = tab.title)},
                    label = { Text(text = tab.title) },
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (currentRoute != tab.route) {
                            navController.navigate(tab.route)
                        }
                    },
                    alwaysShowLabel = false
                )
            }

        }
    }
}

