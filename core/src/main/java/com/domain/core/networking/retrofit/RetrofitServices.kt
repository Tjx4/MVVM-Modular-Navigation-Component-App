package com.domain.core.networking.retrofit

import com.domain.myapplication.models.Currencies
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET("apicurrencies")
    suspend fun currencies(@Query("api_key") apiKey: String): Currencies?
}