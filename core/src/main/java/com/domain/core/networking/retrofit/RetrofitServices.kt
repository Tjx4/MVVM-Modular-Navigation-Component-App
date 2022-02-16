package com.domain.core.networking.retrofit

import com.domain.myapplication.models.Image
import com.domain.myapplication.models.Item
import retrofit2.http.*

interface RetrofitServices {
    @GET("api/items.php")
    suspend fun getItems(@Query("api_key") apiKey: String): List<Item>?
    @GET
    suspend fun getItemImage(@Url url: String): Image?

    @POST("???????")
    suspend fun updatePassword(@Field("data")  data: HashMap<String, String>)
}