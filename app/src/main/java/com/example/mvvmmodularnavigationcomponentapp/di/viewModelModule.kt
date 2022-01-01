package com.example.mvvmmodularnavigationcomponentapp.di

import com.domain.dashboard.DashboardViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tld.domain.favourites.FavouritesViewModel
import tld.domain.login.LoginViewModel

val viewModelModule = module {
    viewModel { LoginViewModel(androidApplication(), get()) }
    viewModel { DashboardViewModel(androidApplication(), get()) }
    viewModel { FavouritesViewModel(androidApplication()) }
}