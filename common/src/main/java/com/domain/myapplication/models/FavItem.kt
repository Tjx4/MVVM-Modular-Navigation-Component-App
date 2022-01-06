package com.domain.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavItem(
    var name: String?,
    var icon: String?,
) : Parcelable