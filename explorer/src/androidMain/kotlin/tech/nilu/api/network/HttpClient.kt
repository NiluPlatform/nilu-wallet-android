@file:JvmName("HttpClientJvmKt")
@file:JvmMultifileClass

package tech.nilu.api.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.observer.*
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

internal actual fun getPlatformHttpClient(): HttpClient = HttpClient(OkHttp) {
    ResponseObserver { println(it.toString()) }
    engine {
        addInterceptor(
            HttpLoggingInterceptor { message -> Log.v("HttpClient", message) }
                .apply { level = HttpLoggingInterceptor.Level.BODY }
        )
        config {
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }
    }
}
