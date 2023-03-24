package bg.gan.composeexam.hiltInjection

import android.app.Application
import bg.gan.composeexam.BuildConfig
import bg.gan.composeexam.model.apiService.ApiService
import bg.gan.composeexam.model.apiService.AuthorizationInterceptor
import bg.gan.composeexam.utilities.BASE_URL
import bg.gan.composeexam.utilities.CACHE_NAME
import bg.gan.composeexam.utilities.jsonDefaultInstance
import com.google.gson.Gson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


//retrofit builder with json serialization and using interceptor
// both for cache and authorization used for token secrity
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    @Provides
    @Singleton
    fun providesOkhttpClient(cache: Cache): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
            .addInterceptor(cacheInterceptor)
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) okHttpClient
            .addInterceptor(loggingInterceptor)
            .addInterceptor(
                AuthorizationInterceptor()
            )
        return okHttpClient.build()
    }

    private val contentType = "application/json".toMediaType()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create())


                // here I can use GsonConverter as factory, but kotlin serialization was used in this case
                //      Json.asConverterFactory(contentType)
//                GsonconverterFactory.
//                    .asConverterFactory(contentType)
//            )
            .build()
    }


    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    private val apiInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val url = originalHttpUrl.newBuilder()
            .build()
        request.url(url)
        chain.proceed(request.build())
    }
    private val cacheInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(30, TimeUnit.DAYS)
            .build()
        response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }


    //When we make a network call to fetch the data from the server.
    // For the very first time, it will get the data from the server and it will cache the HTTP response on the client.
    // Then, if we make the same API call again, it will return the data from the cache instantly.
    @Provides
    @Singleton
    fun providesCache(app: Application): Cache {
        return Cache(
            File(app.applicationContext.cacheDir, CACHE_NAME),
            10485760L
        )
    }
}