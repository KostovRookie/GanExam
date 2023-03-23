package bg.gan.composeexam.navGraph

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController


//navigation for bottom bar with 2 tabs
@Composable
fun DashboardNav(

) {
    val tabs = remember { HomeTabs.values()}
    val navController = rememberNavController()
    rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold (
        scaffoldState = scaffoldState,
        bottomBar = {
            GitAppBottomBar(
                navController = navController, tabs = tabs )
        }
            ){ innerPadding ->
        NavGraph(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )

    }
}