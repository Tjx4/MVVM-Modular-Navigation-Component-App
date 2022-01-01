package com.domain.myapplication.models

data class LoginResponse(
    var user: User?,
    var error: ErrorResponse?
)