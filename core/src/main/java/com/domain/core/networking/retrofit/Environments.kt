package com.domain.core.networking.retrofit

enum class Environments(var url: String, var ip: String) {
    Production("http://appicsoftware.xyz/", "160.153.129.204"),
    Staging("http://appicsoftware.xyz/staging/", ""),
    Dev("http://appicsoftware.xyz/", ""),
    Mock("http://appicsoftware.xyz/", "")
}