package com.domain.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemType(
    @SerializedName("id")  var id: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("time_to_refresh_in_seconds") var timeToRefreshInSeconds: String?,
    @SerializedName("items") var items: List<Item>?,
) : Parcelable