package bg.gan.composeexam.userInterface.layouts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bg.gan.composeexam.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun SplashScreen(
    navToHomeScreen: () -> Unit,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.main_logo))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 100,
        isPlaying = true
    )
    Surface (
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.height(300.dp)
            )
        }

    }
    //little delay to help app load and prepare
    produceState(initialValue = -1){
        delay(3000)
        navToHomeScreen()
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(navToHomeScreen = {})
}