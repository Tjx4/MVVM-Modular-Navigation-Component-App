package com.domain.repositories.weatherRepository

import com.domain.core.networking.retrofit.RetrofitServices
import com.domain.core.persistance.room.ForexDB
import com.domain.myapplication.models.Weather

class WeatherRepositoryImpl(private val retrofitServices: RetrofitServices, private val database: ForexDB) : WeatherRepository {
    override fun getTodaysWeather(): List<Weather>? {
        return null
    }
}