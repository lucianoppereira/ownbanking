package com.yacarex.ownbanking.onboarding.ui.utils

interface FieldValidationListener {
    fun fieldValidationSuccess()
    fun fieldEmptyError(field: Fields)
    fun fieldValidError(field: Fields)
    fun passInconsistencyError()
    fun authenticationError()
}