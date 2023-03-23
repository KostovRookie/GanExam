package bg.gan.composeexam.navGraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import bg.gan.composeexam.userInterface.layouts.UserDetailsScreen
import bg.gan.composeexam.viewModel.UserViewModel


sealed class DashDestinations(val route: String) {
    object HomeRoute : DashDestinations("home")
    object UserDetail: DashDestinations("{user}/detail")



    // creating route for specific user page
    //to navigate to specific details
    fun createRoute(user:String) = "$user/detail"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = DashDestinations.HomeRoute.route,
    userViewModel: UserViewModel = hiltViewModel()
){

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        navigation(
            route = DashDestinations.HomeRoute.route,
            startDestination = HomeTabs.HOME.route
        ){
            addHomeGraph(
                navController = navController,
                modifier = modifier
            )
        }
        composable(
            route = DashDestinations.UserDetail.route
        ){ navBackStackEntry ->
            val userArg = navBackStackEntry.arguments?.getString("user") ?: ""
            val user = remember{
                userViewModel.getUser(userArg)
                userViewModel.user
            }.collectAsState()

            user.value.data?.let { UserDetailsScreen(userResponse = it) {
                navController.navigateUp()
            }
            }
        }
    }
}