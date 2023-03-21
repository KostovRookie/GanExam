package bg.gan.composeexam.model.apiService

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class AuthorizationInterceptor : Interceptor {
    // inserting github token into interceptor as default api request in github appear to be 50
    // without token, token gives 5000 requests
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.header("Accept", "application/json")
        builder.header("Authorization", "token " +
               // "$TOKEN" +
                "")

        return chain.proceed(builder.build())
    }
}
