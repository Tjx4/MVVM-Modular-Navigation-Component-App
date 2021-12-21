package com.domain.repositories.weatherRepository

import com.domain.myapplication.models.Weather

class WeatherRepositoryImpl(private val retrofitServices: String, private val database: String) : WeatherRepository {
    override fun getTodaysWeather(): List<Weather>? {
        return null
    }
}