package com.sparknetwork.personalityassest.util


import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object ApiUtils {
    fun getOKHttpClient() = OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()


    fun getClosedRetrofit(baseURL: String, converterFactory: Converter.Factory) = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(converterFactory)
            .client(getOKHttpClient())
            .build()


    fun getClosedRetrofit(baseURL: String) = getClosedRetrofit(baseURL, MoshiConverterFactory.create())
}