package com.hungvv.base_mvvm_android_3.di

import com.google.gson.JsonParseException
import com.hungvv.base_mvvm_android_3.BuildConfig
import com.hungvv.base_mvvm_android_3.utils.constant.APIConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import org.koin.dsl.module
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.nio.channels.UnresolvedAddressException
import java.util.concurrent.TimeUnit
/**
- Create by :Vy Hùng
- Create at :05,November,2023
 **/

private const val TAG = "NetworkModule"

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
}

private fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)

    return okHttpClient.build()
}

private fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(APIConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}

suspend fun mapError(error: Exception): Exception {
    // with error from http when have base response you will try read body error response from server instead text "Something went wrong"
    when (error) {
        is HttpException -> {
            val message = when(error.response()?.code()) {
                403 -> "Resource Forbidden!"
                404 -> "Resource Not Found!"
                500 -> "Internal Server Error"
                502 -> "Bad GateWay"
                301 -> "Resource Removed"
                else -> error.message
            }
            Timber.e(
                TAG,
                "RedirectResponseException 3xx |CODE = ${error.response()?.code()}, message = ${error.message}",
            )
            return Exception(message)
        }


        is JsonParseException -> {
            Timber.e(TAG, "SerializationException | message = ${error.message}")
            return Exception("Something went wrong!")
        }

        is IOException -> {
            Timber.e(TAG, "IOException | message = ${error.message}")
            return Exception("Please check your network connection!")
        }

        is UnresolvedAddressException -> {
            Timber.e(TAG, "UnresolvedAddressException | message = ${error.message}")
            return Exception("Please check your network connection!")
        }

        else -> {
            Timber.e(TAG, "Unknown | ${error.message}")
            return Exception(error.message)
        }
    }
}