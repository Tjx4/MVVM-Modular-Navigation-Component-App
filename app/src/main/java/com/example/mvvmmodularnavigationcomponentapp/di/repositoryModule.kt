package com.example.mvvmmodularnavigationcomponentapp.di

import com.domain.repositories.weatherRepository.WeatherRepository
import com.domain.repositories.weatherRepository.WeatherRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
}