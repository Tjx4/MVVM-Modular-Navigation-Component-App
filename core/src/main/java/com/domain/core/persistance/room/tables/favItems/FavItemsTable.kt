package com.domain.core.persistance.room.tables.favItems

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "fav_items")
data class FavItemsTable (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name ="title")
    var title: String?,
    @ColumnInfo(name ="type")
    var type: String?,
    @ColumnInfo(name ="product")
    var product: String?,

    @ColumnInfo(name = "imageThumbNail")
    var imageThumbNail: String? = null,
    @ColumnInfo(name = "imageMedium")
    var imageMedium: String? = null,
    @ColumnInfo(name = "imageXl")
    var imageXl: String? = null,

    /*
    @ColumnInfo(name = "cardInfoRel")
    var cardInfoRel: List<String>? = null,
    @ColumnInfo(name = "cardInfoDataMethod")
    var cardInfoMethod: String? = null,
    @ColumnInfo(name = "metaDataHref")
    var cardInfoHref: String? = null,

    @ColumnInfo(name = "updateRel")
    var updateRel: List<String>? = null,
    @ColumnInfo(name = "updateMethod")
    var updateMethod: String? = null,
    @ColumnInfo(name = "updateHref")
    var updateHref: String? = null,

    @ColumnInfo(name = "deleteRel")
    var deleteRel: List<String>? = null,
    @ColumnInfo(name = "deleteMethod")
    var deleteMethod: String? = null,
    @ColumnInfo(name = "deleteHref")
    var deleteHref: String? = null
*/
): Parcelable