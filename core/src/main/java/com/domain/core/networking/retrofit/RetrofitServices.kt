package com.domain.core.networking.retrofit

import com.domain.myapplication.models.*
import retrofit2.http.*

interface RetrofitServices {
    @GET("api/cars/dashboard.php")
    suspend fun getDashBoard(@Query("api_key") apiKey: String): List<ItemCategory>?
    @GET("api/cars/items.php")
    suspend fun getItems(@Query("api_key") apiKey: String): List<Item>?
    @GET("api/cars/pagedItems.php")
    suspend fun getPagedItems(@Query("api_key") apiKey: String, @Query("page")page: Int): PagedItems?
    @GET
    suspend fun getItemImage(@Url url: String): Image?
    @GET("api/cars/itemVideos.php")
    suspend fun getItemVideo(@Query("api_key") apiKey: String, @Query("video_id") videoId: String): Video?
    @GET
    suspend fun getItemCategory(@Url url: String): ItemCategory?
    @GET
    suspend fun cardInfo(@Url url: String): Item?
    @PUT
    suspend fun updateItem(@Url url: String, @Field("data")  data: HashMap<String, String>)
    @DELETE
    suspend fun deleteItem(@Url url: String)
    @POST("???????")
    suspend fun updatePassword(@Field("data")  data: HashMap<String, String>)
}