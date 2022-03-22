package com.domain.myapplication.models

import android.os.Parcelable
import com.domain.myapplication.R
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    @SerializedName("id")  var id: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("type") var type: String?,
    @SerializedName("product") var product: String?,
    @SerializedName("tracking_info") var trackingInfo: HashMap<String, Int>?,
    @SerializedName("image") var image: Image?,
    @SerializedName("links") var links: List<Link>?,
    @Transient var isFav: Boolean,
) : Parcelable