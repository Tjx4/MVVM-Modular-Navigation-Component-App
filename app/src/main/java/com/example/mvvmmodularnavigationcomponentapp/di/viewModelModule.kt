package com.example.mvvmmodularnavigationcomponentapp.di

import com.domain.dashboard.DashboardViewModel
import com.example.downloads.DownloadsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tld.domain.favourites.FavouritesViewModel
import tld.domain.login.LoginViewModel
import tld.domain.videos.VideosViewModel

val viewModelModule = module {
    viewModel { LoginViewModel(androidApplication(), get()) }
    viewModel { DashboardViewModel(androidApplication(), get()) }
    viewModel { FavouritesViewModel(androidApplication()) }
    viewModel { VideosViewModel(androidApplication()) }
    viewModel { DownloadsViewModel(androidApplication()) }
}