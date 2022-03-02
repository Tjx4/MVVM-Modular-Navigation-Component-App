package com.domain.core.networking.retrofit

import com.domain.myapplication.models.Image
import com.domain.myapplication.models.Item
import com.domain.myapplication.models.Video
import retrofit2.http.*

interface RetrofitServices {
    @GET("api/cars/items.php")
    suspend fun getItems(@Query("api_key") apiKey: String): List<Item>?
    @GET
    suspend fun getItemImage(@Url url: String): Image?
    @GET("api/cars/itemVideos.php")
    suspend fun getItemVideo(@Query("api_key") apiKey: String, @Query("video_id") videoId: String): Video?

    @POST("???????")
    suspend fun updatePassword(@Field("data")  data: HashMap<String, String>)
}