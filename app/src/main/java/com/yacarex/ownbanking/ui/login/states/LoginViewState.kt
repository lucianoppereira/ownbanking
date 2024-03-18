package com.yacarex.ownbanking.ui.login.states

import com.yacarex.ownbanking.core.utils.Constants
import com.yacarex.ownbanking.core.utils.Constants.STATE_NEUTRAL

data class LoginViewState(
    val isLoading: Boolean = false,
    val isValidEmail: String = STATE_NEUTRAL,
    val isValidPassword: String = STATE_NEUTRAL
) {
    fun userValidated() =
        isValidEmail == Constants.STATE_VALID && isValidPassword == Constants.STATE_VALID
}