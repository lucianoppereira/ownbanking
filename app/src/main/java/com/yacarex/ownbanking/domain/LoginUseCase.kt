package com.yacarex.ownbanking.domain

import com.yacarex.ownbanking.data.network.AuthenticationService
import com.yacarex.ownbanking.data.response.LoginResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authenticationService: AuthenticationService) {

    suspend operator fun invoke(email: String, password: String): LoginResponse =
        authenticationService.login(email, password)
}