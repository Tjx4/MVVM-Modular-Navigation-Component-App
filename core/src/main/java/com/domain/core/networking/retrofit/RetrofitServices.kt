package com.domain.core.networking.retrofit

import com.domain.myapplication.models.Image
import com.domain.myapplication.models.Item
import com.domain.myapplication.models.ItemCategory
import com.domain.myapplication.models.Video
import retrofit2.http.*

interface RetrofitServices {
    @GET("api/cars/dashboard.php")
    suspend fun getDashBoard(@Query("api_key") apiKey: String): List<ItemCategory>?
    @GET("api/cars/items.php")
    suspend fun getItems(@Query("api_key") apiKey: String): List<Item>?
    @GET
    suspend fun getItemImage(@Url url: String): Image?
    @GET("api/cars/itemVideos.php")
    suspend fun getItemVideo(@Query("api_key") apiKey: String, @Query("video_id") videoId: String): Video?
    @GET
    suspend fun getItemCategory(@Url url: String): ItemCategory?

    @POST("???????")
    suspend fun updatePassword(@Field("data")  data: HashMap<String, String>)
}