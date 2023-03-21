package bg.gan.composeexam.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elders.eldersjunior.ui.screens.SplashScreen


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