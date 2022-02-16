package com.domain.repositories.items

import com.domain.myapplication.models.Image
import com.domain.myapplication.models.Item

interface ItemsRepository {
    suspend fun getRemoteItems(): List<Item>?
    suspend fun getItemImage(itemId: String): Image?
    suspend fun getFavourites(): List<Item>?
    suspend fun saveFavouriteItem(item: Item)
    suspend fun saveFavouriteItems(favourites: List<Item>)
    suspend fun clearItems()
}