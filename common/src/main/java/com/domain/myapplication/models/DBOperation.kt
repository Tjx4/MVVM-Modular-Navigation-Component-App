package com.domain.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class DBOperation (
    open var isSuccessful: Boolean,
    open var message: String? = null
)