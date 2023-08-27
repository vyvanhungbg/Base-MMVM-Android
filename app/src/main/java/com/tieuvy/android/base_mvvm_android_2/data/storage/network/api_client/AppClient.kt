package com.tieuvy.android.base_mvvm_android_2.data.storage.network.api_client

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.tieuvy.android.base.extension.isNetworkAvailable
import com.tieuvy.android.base_mvvm_android_2.data.storage.network.api_client.API.CACHE_CONTROL
import com.tieuvy.android.base_mvvm_android_2.data.storage.network.api_client.API.TIME_CACHE_OFFLINE
import com.tieuvy.android.base_mvvm_android_2.data.storage.network.api_client.API.TIME_CACHE_ONLINE
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.statement.bodyAsText
import io.ktor.http.headers
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Request
import java.io.IOException


private const val TAG = "AppClient"

object API {
    const val BASE = "https://api.github.com"
    const val SEARCH_USER = "/search/users"

    const val CACHE_SIZE = (10 * 1024 * 1024).toLong()
    const val READ_TIMEOUT = 5000
    const val WRITE_TIMEOUT = 5000
    const val CONNECT_TIMEOUT = 5000
    const val CACHE_CONTROL = "Cache-Control"
    const val TIME_CACHE_ONLINE = "public, max-age = 60" // 1 minute

     const val TIME_CACHE_OFFLINE = "public, only-if-cached, max-stale = 86400" //1 day

}

fun provideChucker(context: Context) = ChuckerInterceptor.Builder(context)
    .collector(ChuckerCollector(context))
    .maxContentLength(250000L)
    .redactHeaders(emptySet())
    .alwaysReadResponseBody(false)
    .build() // tạo giá trị

class CustomHeaderPluginConfig {
    var cacheControl: String = "Cache-Control"
    var timeCacheOnline: String = "public, max-age = 60"
    var timeCacheOffLine: String = "public, only-if-cached, max-stale = 86400"
}

val CustomHeaderConfigurablePlugin =
    createClientPlugin("CustomHeaderConfigurablePlugin", ::CustomHeaderPluginConfig) {
        val cacheControl = pluginConfig.cacheControl
        val timeCacheOnline = pluginConfig.timeCacheOnline
        val timeCacheOffline = pluginConfig.timeCacheOffLine

        onRequest { request, _ ->
            request.headers.append(cacheControl, timeCacheOnline)
           // request.headers.append(cacheControl, timeCacheOffline)
        }
    }

object AppClient {
    fun provideKtorClient(context: Context): HttpClient {
        return HttpClient(OkHttp.create {
            addInterceptor(provideChucker(context))
            addInterceptor{ chain ->
                var request: Request = chain.request()
                request = if (context.isNetworkAvailable()) {
                    request
                        .newBuilder()
                        .header(CACHE_CONTROL, TIME_CACHE_ONLINE)
                        .build()
                } else {
                    request
                        .newBuilder()
                        .header(CACHE_CONTROL, TIME_CACHE_OFFLINE)
                        .build()
                }
                val httpUrl: HttpUrl = request.url
                    .newBuilder()
                    .build()
                val requestBuilder: Request.Builder = request
                    .newBuilder()
                    .url(httpUrl)
                chain.proceed(requestBuilder.build())
            }
        }) {
            headers {
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 50_000
                connectTimeoutMillis = 50_000
            }
            defaultRequest {
                url(API.BASE)
            }
            install(Resources)
            install(ContentNegotiation) {
                json(Json {
                    /*  prettyPrint = true
                      explicitNulls = true*/
                    isLenient = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true // default val
                })
            }

            install(HttpCache) {

            }

            install(HttpRequestRetry) {
                maxRetries = 3
                retryIf { request, response ->
                    !response.status.isSuccess()
                }
                retryOnExceptionIf { _, cause -> // when lost network
                    cause is UnresolvedAddressException || cause is IOException || cause is ServerResponseException
                }
                delayMillis { retry ->
                    retry * 3000L
                } // retries in 3, 6,
            }





            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.e("HTTP Client", message)
                    }
                }

                /*filter { request ->
                    request.url.host.contains("ktor.io")
                }
                sanitizeHeader { header -> header == HttpHeaders.Authorization }*/
            }
            expectSuccess = true

        }
    }

    suspend fun mapError(error: Exception): Exception {
        // with error from http when have base response you will try read body error response from server instead text "Something went wrong"
        when (error) {
            is RedirectResponseException -> {
                val body = error.response.bodyAsText()
                Log.e(
                    TAG,
                    "RedirectResponseException 3xx |CODE = ${error.response.status.value}, message = ${body}",
                )
                return Exception("Something went wrong!")
            }

            is ClientRequestException -> {
                val body = error.response.bodyAsText()
                Log.e(
                    TAG,
                    "ClientRequestException 4xx | CODE = ${error.response.status.value}, message = ${body}",
                )
                return Exception("Something went wrong!")
            }

            is ServerResponseException -> {
                val body = error.response.bodyAsText()
                Log.e(
                    TAG,
                    "ClientRequestException 4xx | CODE = ${error.response.status.value}, message = ${body}"
                )
                return Exception("Server Not responding!")
            }

            is SerializationException -> {
                Log.e(TAG, "SerializationException | message = ${error.message}")
                return Exception("Something went wrong!")
            }

            is IOException -> {
                Log.e(TAG, "IOException | message = ${error.message}")
                return Exception("Please check your network connection!")
            }

            is UnresolvedAddressException -> {
                Log.e(TAG, "UnresolvedAddressException | message = ${error.message}")
                return Exception("Please check your network connection!")
            }

            else -> {
                Log.e(TAG, "Unknown | ${error.message}")
                return Exception(error.message)
            }
        }
    }
}