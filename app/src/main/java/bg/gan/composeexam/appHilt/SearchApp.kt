package bg.gan.composeexam.appHilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


//top level declaration of Hilt implementation
@HiltAndroidApp
class SearchApp: Application()