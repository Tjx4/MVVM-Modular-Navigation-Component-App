package com.domain.core.networking.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object API {
    var retrofit: RetrofitServices

    init {
        val builder = Retrofit
            .Builder()
            .baseUrl(Environments.Mock.url)
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()
        API.retrofit = retrofit.create(RetrofitServices::class.java)
    }
}

