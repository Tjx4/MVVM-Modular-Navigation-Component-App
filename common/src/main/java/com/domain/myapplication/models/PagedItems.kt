package com.domain.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PagedItems(
    @SerializedName("pages")  var pages: Int?,
    @SerializedName("page")  var page: Int?,
    @SerializedName("items")  var items: List<Item>?
) : Parcelable