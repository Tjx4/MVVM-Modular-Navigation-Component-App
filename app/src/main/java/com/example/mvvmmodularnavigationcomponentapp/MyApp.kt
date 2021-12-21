package com.example.mvvmmodularnavigationcomponentapp

import android.app.Application
import com.example.mvvmmodularnavigationcomponentapp.di.ModuleLoadHelper
import com.example.mvvmmodularnavigationcomponentapp.di.libraryModule
import com.example.mvvmmodularnavigationcomponentapp.di.repositoryModule
import com.example.mvvmmodularnavigationcomponentapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    libraryModule
                ) + ModuleLoadHelper.getBuildSpecialModuleList()
            )
        }
    }
}