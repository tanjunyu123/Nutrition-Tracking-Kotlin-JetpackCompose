package com.example.assignment1.junyu32025130.api.service

import com.example.assignment1.junyu32025130.api.model.Fruit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FruityViceApiService {
    @GET("api/fruit/{name}")
    fun getFruitsByName(
        @Path("name") name: String
    ): Call<Fruit>
}