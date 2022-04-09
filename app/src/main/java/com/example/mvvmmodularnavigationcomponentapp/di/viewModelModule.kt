package com.example.mvvmmodularnavigationcomponentapp.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tld.domain.viewmodels.*

val viewModelModule = module {
    viewModel { LoginViewModel(androidApplication(), get()) }
    viewModel { DashboardViewModel(androidApplication(), get(), get()) }
    viewModel { FavouritesViewModel(androidApplication(), get()) }
    viewModel { ViewItemViewModel(androidApplication()) }
    viewModel { VideosViewModel(androidApplication(), get()) }
    viewModel { LatestViewModel(androidApplication()) }
    viewModel { DownloadsViewModel(androidApplication()) }
    viewModel { MoreViewModel(androidApplication()) }
    viewModel { PlayerViewModel(androidApplication(), get(), get()) }
}