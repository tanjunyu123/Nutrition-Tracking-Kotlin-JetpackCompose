package com.example.assignment1.junyu32025130.api.client

import com.example.assignment1.junyu32025130.api.service.FruityViceApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.fruityvice.com/"

    val apiService: FruityViceApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FruityViceApiService::class.java)
    }
}