package com.domain.repositories.items

import com.domain.myapplication.models.DBOperation
import com.domain.myapplication.models.Image
import com.domain.myapplication.models.Item

interface ItemsRepository {
    suspend fun getRemoteItems(): List<Item>?
    suspend fun getItemImage(url: String): Image?
    suspend fun getFavourites(): List<Item>?
    suspend fun saveItemFavourites(item: Item): DBOperation
    suspend fun saveItemsToFavourites(favourites: List<Item>): DBOperation
    suspend fun removeItemFromFavourites(item: Item): DBOperation
    suspend fun clearItems(): DBOperation
}