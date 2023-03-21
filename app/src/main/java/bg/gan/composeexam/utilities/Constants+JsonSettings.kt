package bg.gan.composeexam.utilities

import kotlinx.serialization.json.Json

val jsonDefaultInstance =
    Json {
        ignoreUnknownKeys = true; isLenient = true; encodeDefaults = false
    }
const val BASE_URL = "https://api.github.com/"
const val CACHE_NAME = "elder-cache"
const val TOKEN = ""