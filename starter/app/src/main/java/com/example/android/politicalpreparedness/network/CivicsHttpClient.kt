package com.example.android.politicalpreparedness.network

import android.util.Log
import androidx.databinding.library.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
/*
private const val BASE_URL = "https://api.nasa.gov/"
private const val API_KEY = "5qQvW5DyB6tRlE7qyWLftAFMGs1gCag701mTUs8H"
*/



private const val BASE_URL = "https://www.googleapis.com/civicinfo/v2/"
private const val API_KEY = "AIzaSyBq52QAE9EE2J8SH8j43hFYfz1pUq21JKU"

object RetrofitClient {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private var retrofit: Retrofit? = null

    private fun getClientHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val url = original
                    .url
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = original
                    .newBuilder()
                    .url(url)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    /* private fun getClientTwo(): Retrofit {
        when (retrofit) {
            null -> {

                retrofit = Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(getClientHttp())
                    .baseUrl(BASE_URL)
                    .build()
                Log.d("retrofitSVC", "getClient: $BASE_URL")

            }
        }
        Log.d("retrofitSVC", "getClient: ${retrofit?.baseUrl()?.queryParameterNames}")
        return retrofit as Retrofit
    }*/

    private fun getClient(): Retrofit {
        when (retrofit) {
            null -> {

                retrofit = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(getClientHttp())
                    .baseUrl(BASE_URL)
                    .build()
                Log.d("retrofitSVC", "getClient: $BASE_URL")

            }
        }
        Log.d("retrofitSVC", "getClient: ${retrofit?.baseUrl()?.queryParameterNames}")
        return retrofit as Retrofit
    }

    val retrofitService: CivicsApiService by lazy {
        getClient().create(CivicsApiService::class.java)
    }
}
