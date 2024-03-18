package com.yacarex.ownbanking.ui.onboarding.model

class SignUpFormData(
    var name: String,
    var surname: String,
    var email: String,
    var pass: String,
    var passConfirm: String
) {
    fun isNotEmpty() = name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() &&
                pass.isNotEmpty() && passConfirm.isNotEmpty()
}