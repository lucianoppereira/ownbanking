package com.yacarex.ownbanking.data.response

sealed class LoginResponse {
    object Error : LoginResponse()
    data class Success(val verified: Boolean) : LoginResponse()
}
