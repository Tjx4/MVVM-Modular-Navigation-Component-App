package com.domain.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    @SerializedName("id")  var id: String?,
    @SerializedName("itemName") var itemName: String?,
    @SerializedName("image") var image: Image?,
    @SerializedName("metaData") var metaData: String?,
) : Parcelable, Fav()