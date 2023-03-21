package bg.gan.composeexam

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import bg.gan.composeexam.navGraph.MainNavGraph
import bg.gan.composeexam.userInterface.theme.ElderAppTheme

@Composable
fun GanApp() {

    ElderAppTheme {
        val navController = rememberNavController()
        MainNavGraph(
            navController = navController
        )
    }
}