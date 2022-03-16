package com.domain.repositories.items

import com.domain.myapplication.models.*

interface ItemsRepository {
    suspend fun getCategorizedItems(): List<ItemCategory>?
    suspend fun getRemoteItems(): List<Item>?
    suspend fun getItemImage(url: String): Image?
    suspend fun getItemVideo(id: String): APIResponse
    suspend fun getFavourites(): List<Item>?
    suspend fun saveItemFavourites(item: Item): DBOperation
    suspend fun saveItemsToFavourites(favourites: List<Item>): DBOperation
    suspend fun removeItemFromFavourites(item: Item): DBOperation
    suspend fun clearItems(): DBOperation
}