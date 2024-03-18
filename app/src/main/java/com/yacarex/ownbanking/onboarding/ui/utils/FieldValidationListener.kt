package com.yacarex.ownbanking.onboarding.ui.utils

import com.yacarex.ownbanking.ui.onboarding.utils.Fields

interface FieldValidationListener {
    fun fieldValidationSuccess()
    fun fieldEmptyError(field: Fields)
    fun fieldValidError(field: Fields)
    fun passInconsistencyError()
    fun authenticationError()
}