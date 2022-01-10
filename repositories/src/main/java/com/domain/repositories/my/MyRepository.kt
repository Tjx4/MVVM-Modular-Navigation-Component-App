package com.domain.repositories.my

import com.domain.myapplication.models.FavItem

interface MyRepository {
    fun getFavourites(): List<FavItem>?
    fun saveFavouriteItem(favItem: FavItem)
    fun saveFavouritesItems(favourites: List<FavItem>)
}