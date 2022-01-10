package com.domain.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var userName: String?,
    var firstName: String?,
    var profilePic: Image?,
): Parcelable