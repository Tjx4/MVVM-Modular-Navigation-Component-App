package com.domain.core.networking.retrofit

import com.domain.myapplication.models.Image
import com.domain.myapplication.models.Item
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitServices {
    @GET("api/items.php")
    suspend fun getItems(@Query("api_key") apiKey: String): List<Item>?
    @GET("api/itemImages.php")
    suspend fun getItemImage(@Query("api_key") apiKey: String, @Query("itemId") itemId: String): Image?

    @POST("???????")
    suspend fun updatePassword(@Field("data")  data: HashMap<String, String>)
}