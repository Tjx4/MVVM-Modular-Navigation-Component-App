package com.example.mvvmmodularnavigationcomponentapp.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tld.domain.viewmodels.*

val viewModelModule = module {
    viewModel { LoginViewModel(androidApplication(), get(), get()) }
    viewModel { DashboardViewModel(androidApplication(), get(), get()) }
    viewModel { FavouritesViewModel(androidApplication()) }
    viewModel { ViewFavouriteViewModel(androidApplication()) }
    viewModel { VideosViewModel(androidApplication()) }
    viewModel { DownloadsViewModel(androidApplication()) }
}