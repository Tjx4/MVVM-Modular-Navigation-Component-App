package com.domain.repositories.items

import com.domain.core.networking.retrofit.RetrofitServices
import com.domain.core.persistance.room.MySqliteDB
import com.domain.core.persistance.room.tables.favItems.FavItemsTable
import com.domain.myapplication.constants.API_KEY
import com.domain.myapplication.extensions.getUniqueString
import com.domain.myapplication.models.Item
import com.domain.myapplication.models.Image

class ItemsRepositoryImpl(private val retrofitServices: RetrofitServices, private val database: MySqliteDB) : ItemsRepository {

    override suspend fun getRemoteItems(): List<Item>? {
        return retrofitServices.getItems(API_KEY)
    }

    override suspend fun getItemImage(url: String): Image? {
        return retrofitServices.getItemImage(API_KEY, itemId)
    }

    override suspend fun getFavourites(): List<Item>? {
        val favItems = ArrayList<Item>()

        val favItemTables = database.favItemsDAO.getAllItems()
        favItemTables?.forEach {
            val image = Image(it.imageThumbNail, it.imageMedium, it.imageXl)
            val favItem = Item(getUniqueString(), it.itemName, image, "")
            favItems.add(favItem)
        }

        return favItems
    }

    override suspend fun saveFavouriteItems(favourites: List<Item>) {
        val favouriteTables = ArrayList<FavItemsTable>()

        favourites?.forEach {
            val favItemTable = FavItemsTable(itemName = it.itemName, imageThumbNail = it.image?.thumbNail,  imageMedium = it.image?.medium, imageXl = it.image?.xl)
            favouriteTables.add(favItemTable)
        }

        database.favItemsDAO.insertAll(favouriteTables)
    }

    override suspend fun saveFavouriteItem(item: Item) {
        val favItemTable = FavItemsTable(itemName = item.itemName, imageThumbNail = item.image?.thumbNail,  imageMedium = item.image?.medium, imageXl = item.image?.xl)
        database.favItemsDAO.insert(favItemTable)
    }

    override suspend fun clearItems() {
        database.favItemsDAO.clear()
    }

}