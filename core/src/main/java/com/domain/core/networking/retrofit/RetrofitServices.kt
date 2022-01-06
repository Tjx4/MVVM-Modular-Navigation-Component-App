package com.domain.core.networking.retrofit

import com.domain.myapplication.models.Weather
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitServices {
    @GET("???????")
    suspend fun getService(@Query("api_key") apiKey: String, @Query("from") from: String): Weather?

    @POST("???????")
    suspend fun postService(@Field("data")  data: HashMap<String, String>)
}