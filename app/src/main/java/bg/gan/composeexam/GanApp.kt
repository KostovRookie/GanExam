package bg.gan.composeexam

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import bg.gan.composeexam.navGraph.MainNavGraph
import bg.gan.composeexam.userInterface.theme.ElderAppTheme



//top level composable function to define navController
@Composable
fun GanApp() {

    ElderAppTheme {
        val navController = rememberNavController()
        MainNavGraph(
            navController = navController
        )
    }
}