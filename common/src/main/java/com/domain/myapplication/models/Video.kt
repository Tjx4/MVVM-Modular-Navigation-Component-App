package com.domain.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    @SerializedName("low")  var low: String?,
    @SerializedName("mid") var mid: String?,
    @SerializedName("high") var high: String?,
) : Parcelable