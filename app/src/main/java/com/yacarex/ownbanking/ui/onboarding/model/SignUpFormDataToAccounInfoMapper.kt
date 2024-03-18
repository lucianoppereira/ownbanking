package com.yacarex.ownbanking.ui.onboarding.model

class SignUpFormDataToAccounInfoMapper {

    fun mapToUserInfo(signUpFormData: SignUpFormData) = AccountInfo(
        signUpFormData.name,
        signUpFormData.surname,
        signUpFormData.email
    )
}