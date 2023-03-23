package bg.gan.composeexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import bg.gan.composeexam.userInterface.theme.ElderAppTheme
import dagger.hilt.android.AndroidEntryPoint


//hilt top level declaration and setting of compose entry point
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElderAppTheme {
                GanApp()
            }
        }
    }
}