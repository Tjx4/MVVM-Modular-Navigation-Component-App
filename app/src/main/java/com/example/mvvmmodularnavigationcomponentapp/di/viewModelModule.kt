package com.example.mvvmmodularnavigationcomponentapp.di

import com.domain.dashboard.DashboardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tld.domain.favourites.FavouritesViewModel

val viewModelModule = module {
    viewModel { DashboardViewModel(androidApplication(), get()) }
    viewModel { FavouritesViewModel(androidApplication()) }
}