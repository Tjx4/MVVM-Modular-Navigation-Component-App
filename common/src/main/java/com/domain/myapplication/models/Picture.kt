package com.domain.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Picture (
    @SerializedName("thumbNail") open var thumbNail: String? = null,
    @SerializedName("medium") open var medium: String? = null,
    @SerializedName("xl") open var xl: String? = null
): Parcelable