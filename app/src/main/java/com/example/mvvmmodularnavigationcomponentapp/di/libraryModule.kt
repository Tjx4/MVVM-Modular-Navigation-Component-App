package com.example.mvvmmodularnavigationcomponentapp.di

import com.domain.core.networking.retrofit.API
import com.domain.core.persistance.room.ForexDB
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val libraryModule = module {
   single { API.retrofit }
   single { ForexDB.getInstance(androidApplication()) }
   single { FirebaseCrashlytics.getInstance() }
}