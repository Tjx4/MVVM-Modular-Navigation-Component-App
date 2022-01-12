package com.domain.repositories.items

import com.domain.myapplication.models.FavItem

interface ItemsRepository {
    suspend fun getRemoteItems(userId: String): List<FavItem>?
    suspend fun getFavourites(): List<FavItem>?
    suspend fun saveFavouriteItem(favItem: FavItem)
    suspend fun saveFavouriteItems(favourites: List<FavItem>)
    suspend fun clearItems()
}