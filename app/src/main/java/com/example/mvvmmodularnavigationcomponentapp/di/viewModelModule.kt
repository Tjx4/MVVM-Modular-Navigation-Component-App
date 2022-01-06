package com.example.mvvmmodularnavigationcomponentapp.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tld.domain.viewmodels.FavouritesViewModel
import tld.domain.viewmodels.LoginViewModel
import tld.domain.viewmodels.VideosViewModel

val viewModelModule = module {
    viewModel { LoginViewModel(androidApplication(), get()) }
    viewModel { tld.domain.viewmodels.DashboardViewModel(androidApplication(), get()) }
    viewModel { FavouritesViewModel(androidApplication()) }
    viewModel { VideosViewModel(androidApplication()) }
    viewModel { tld.domain.viewmodels.DownloadsViewModel(androidApplication()) }
}