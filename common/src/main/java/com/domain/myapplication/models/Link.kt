package com.domain.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Link(
    @SerializedName("rel")  var rel: List<String>?,
    @SerializedName("method")  var method: String?,
    @SerializedName("href")  var href: String?
) : Parcelable