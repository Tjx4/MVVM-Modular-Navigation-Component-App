package com.domain.myapplication.models

import com.google.gson.annotations.SerializedName

open class ResponseError(
    @SerializedName("code") var code: String? = null,
    @SerializedName("info") var info: String? = null
)
