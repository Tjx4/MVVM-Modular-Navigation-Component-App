package com.example.mvvmmodularnavigationcomponentapp.di

import com.domain.repositories.authentication.AuthenticationRepository
import com.domain.repositories.authentication.AuthenticationRepositoryImpl
import com.domain.repositories.my.MyRepository
import com.domain.repositories.my.MyRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MyRepository> { MyRepositoryImpl(get(), get()) }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get(), get()) }
}