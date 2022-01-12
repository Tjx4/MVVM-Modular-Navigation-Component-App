package com.domain.repositories.items

import com.domain.core.networking.retrofit.RetrofitServices
import com.domain.core.persistance.room.MySqliteDB
import com.domain.core.persistance.room.tables.favItems.FavItemsTable
import com.domain.myapplication.constants.API_KEY
import com.domain.myapplication.models.FavItem
import com.domain.myapplication.models.Image

class ItemsRepositoryImpl(private val retrofitServices: RetrofitServices, private val database: MySqliteDB) : ItemsRepository {
    override suspend fun getRemoteItems(userId: String): List<FavItem>? {
        return retrofitServices.getItems(API_KEY, userId)
    }

    override suspend fun getFavourites(): List<FavItem>? {
        val favItems = ArrayList<FavItem>()

        val favItemTables = database.favItemsDAO.getAllItems()
        favItemTables?.forEach {
            val image = Image(it.imageThumbNail, it.imageMedium, it.imageXl)
            val favItem = FavItem(it.itemName, image)
            favItems.add(favItem)
        }

        return favItems
    }

    override suspend fun saveFavouriteItems(favourites: List<FavItem>) {
        val favouriteTables = ArrayList<FavItemsTable>()

        favourites?.forEach {
            val favItemTable = FavItemsTable(itemName = it.itemName, imageThumbNail = it.image?.thumbNail,  imageMedium = it.image?.medium, imageXl = it.image?.xl)
            favouriteTables.add(favItemTable)
        }

        database.favItemsDAO.insertAll(favouriteTables)
    }

    override suspend fun saveFavouriteItem(favItem: FavItem) {
        val favItemTable = FavItemsTable(itemName = favItem.itemName, imageThumbNail = favItem.image?.thumbNail,  imageMedium = favItem.image?.medium, imageXl = favItem.image?.xl)
        database.favItemsDAO.insert(favItemTable)
    }

}