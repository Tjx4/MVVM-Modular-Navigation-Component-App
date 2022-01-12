package com.domain.repositories.items

import com.domain.myapplication.models.FavItem

interface ItemsRepository {
    suspend fun getRemoteFavourites(userId: String): List<FavItem>?
    suspend fun getFavourites(): List<FavItem>?
    suspend fun saveFavouriteItem(favItem: FavItem)
    suspend fun saveFavouritesItems(favourites: List<FavItem>)
}