package bg.gan.composeexam.userInterface.layouts

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val state = userViewModel.state.value
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.padding(top = 30.dp),
        scaffoldState = scaffoldState,
        topBar = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), verticalArrangement = Arrangement.Top) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = ParagraphStyle(lineHeight = 30.sp)
                        ) {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    fontSize = 29.sp
                                )
                            ) {
                                append("Търсачка потребители\n в Github")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.Black,
                                    fontSize = 29.sp
                                )
                            ) {
                                // append("потребителско име")
                            }
                        }
                    }
                )
                SearchField(state = state, userViewModel =userViewModel )
            }
        },
        content = { padding ->
            Box(modifier = modifier.fillMaxSize()) {
                state.data?.let {
                    ProfileDetailsScreen(it, navController = navController)
                }
            }
            // Display loading state of UI
            if (state.isLoading || state.data == null) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = modifier.fillMaxSize()) {
                        LoadingLottieAnimation()
                    }
                }
            }
            if (state.error) {
                Column(
                    modifier = modifier.fillMaxSize().padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = modifier.fillMaxSize()) {
                        ErrorLottieAnimation()
                        Toast.makeText(
                            context,
                            "Няма такъв потребител.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    )
}