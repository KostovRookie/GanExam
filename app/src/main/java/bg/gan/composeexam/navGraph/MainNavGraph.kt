package bg.gan.composeexam.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import bg.gan.composeexam.userInterface.layouts.SplashScreen

//main navigation with splash screen animation
//error persists when going back you can still view Splash screen
object MainDestination {
    const val SPLASH_SCREEN_ROUTE = "splash"
}
enum class MainScreen(val route: String) {
    SPLASH("splash"),
    HOME("home")
}

@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.SPLASH.route,
    ){
        composable(
            route = MainScreen.SPLASH.route,
        ){
            SplashScreen(navToHomeScreen = {navController.navigate((DashDestinations.HomeRoute.route))})
        }
        composable(
            route = MainScreen.HOME.route
        ){
            DashboardNav(
            )
        }
    }
}