package com.yacarex.ownbanking.core.utils

object Constants {

    //Validations
    const val MIN_PASSWORD_LENGTH = 8
    const val MIN_NAME_LENGTH = 1
    const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

    //States
    const val STATE_NEUTRAL = "neutral"
    const val STATE_VALID = "valid"
    const val STATE_EMPTY = "empty"
    const val STATE_INVALID_MAIL = "invalid_mail"
    const val STATE_INVALID_PASS = "invalid_pass"
    const val STATE_INCONSISTENCY = "inconsistency"
}