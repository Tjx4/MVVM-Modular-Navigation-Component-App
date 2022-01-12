package com.example.mvvmmodularnavigationcomponentapp.di

import com.domain.repositories.authentication.AuthenticationRepository
import com.domain.repositories.authentication.AuthenticationRepositoryImpl
import com.domain.repositories.items.ItemsRepository
import com.domain.repositories.items.ItemsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<ItemsRepository> { ItemsRepositoryImpl(get(), get()) }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get(), get()) }
}