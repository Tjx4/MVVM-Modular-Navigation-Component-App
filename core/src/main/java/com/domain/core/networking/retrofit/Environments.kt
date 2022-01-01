package com.domain.core.networking.retrofit

enum class Environments(var url: String) {
    Production("https://fxmarketapi.com/"),
    UAT("https://fxmarketapi.com/")
}