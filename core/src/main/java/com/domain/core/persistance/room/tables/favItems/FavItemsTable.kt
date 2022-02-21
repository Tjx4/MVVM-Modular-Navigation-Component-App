package com.domain.core.persistance.room.tables.favItems

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "fav_items")
data class FavItemsTable (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name ="itemName")
    var itemName: String?,
    @ColumnInfo(name = "imageThumbNail")
    var imageThumbNail: String? = null,
    @ColumnInfo(name = "imageMedium")
    var imageMedium: String? = null,
    @ColumnInfo(name = "imageXl")
    var imageXl: String? = null
): Parcelable