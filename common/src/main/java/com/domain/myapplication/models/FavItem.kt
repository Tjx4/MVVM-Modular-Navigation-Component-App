package com.domain.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavItem(
    var itemName: String?,
    var image: Image?,
) : Parcelable