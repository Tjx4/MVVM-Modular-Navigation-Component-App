package com.domain.repositories.weatherRepository

import com.domain.myapplication.models.Weather

interface WeatherRepository {
    fun getTodaysWeather(): List<Weather>?
}