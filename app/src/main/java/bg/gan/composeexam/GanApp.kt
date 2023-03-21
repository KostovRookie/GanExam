package bg.gan.composeexam

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import bg.gan.composeexam.navGraph.MainNavGraph
import bg.gan.composeexam.ui.theme.ElderAppTheme

@Composable
fun GanApp() {

    ElderAppTheme {
        val navController = rememberNavController()
        MainNavGraph(
            navController = navController
        )
    }
}