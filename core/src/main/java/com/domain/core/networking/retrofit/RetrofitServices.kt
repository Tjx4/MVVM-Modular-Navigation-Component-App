package com.domain.core.networking.retrofit

import com.domain.myapplication.models.FavItem
import com.domain.myapplication.models.Weather
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitServices {
    @GET("???????")
    suspend fun getItems(@Query("api_key") apiKey: String, @Query("userId") userId: String): List<FavItem>?

    @POST("???????")
    suspend fun updatePassword(@Field("data")  data: HashMap<String, String>)
}