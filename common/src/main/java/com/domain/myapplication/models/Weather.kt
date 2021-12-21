package com.domain.myapplication.models

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id") var id: String? = null
)