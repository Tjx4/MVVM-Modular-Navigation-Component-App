package com.domain.myapplication.models

import com.google.gson.annotations.SerializedName

data class Currencies(
    @SerializedName("currencies") var currencies: HashMap<String?, String?>?,
    @SerializedName("error") var error: ResponseError?
)
