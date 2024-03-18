package com.yacarex.ownbanking.domain

import com.yacarex.ownbanking.data.network.AuthenticationService
import com.yacarex.ownbanking.data.network.UserService
import com.yacarex.ownbanking.ui.onboarding.model.SignUpFormData
import com.yacarex.ownbanking.ui.onboarding.model.SignUpFormDataToAccounInfoMapper
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
){

    suspend operator fun invoke(signUpFormData: SignUpFormData): Boolean {
        val accountCreated =
            authenticationService.createAccount(signUpFormData.email, signUpFormData.pass) != null
        return if (accountCreated) {
            userService.createUserTable(
                SignUpFormDataToAccounInfoMapper().mapToUserInfo(signUpFormData)
            )
        } else {
            false
        }
    }

    suspend fun signUpCancel() {
        authenticationService.deleteAccount()
    }
}