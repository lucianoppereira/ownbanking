package com.yacarex.ownbanking.ui.onboarding.states

import com.yacarex.ownbanking.core.utils.Constants.STATE_NEUTRAL
import com.yacarex.ownbanking.core.utils.Constants.STATE_VALID

class SignUpViewState(
    var isLoading: Boolean = false,
    var isValidEmail: String = STATE_NEUTRAL,
    var isValidPassword: String = STATE_NEUTRAL,
    var isValidSurname: String = STATE_NEUTRAL,
    var isValidName: String = STATE_NEUTRAL
){

    fun userValidated() = isValidEmail == STATE_VALID && isValidPassword == STATE_VALID
            && isValidSurname == STATE_VALID && isValidName == STATE_VALID
}