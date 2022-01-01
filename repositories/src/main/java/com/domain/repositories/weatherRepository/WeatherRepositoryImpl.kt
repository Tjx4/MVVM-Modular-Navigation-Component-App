package com.domain.repositories.weatherRepository

import com.domain.core.networking.retrofit.RetrofitServices
import com.domain.core.persistance.room.MySqliteDB
import com.domain.myapplication.models.Weather

class WeatherRepositoryImpl(private val retrofitServices: RetrofitServices, private val database: MySqliteDB) : WeatherRepository {
    override fun getTodaysWeather(): List<Weather>? {
        return null
    }
}