package com.moneyfwd.githubusers.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory(private val authInterceptor: AuthInterceptor) {
    operator fun invoke(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            //.client(buildNetworkClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun buildNetworkClient() = OkHttpClient.Builder().addInterceptor(authInterceptor).build()
}