package com.yacarex.ownbanking.ui.onboarding.model

data class AccountInfo (
    val userName: String,
    val userSurname: String,
    val userEmail: String,
    val identificationId: String? = null,
    val movements: UserMovements? = null
) {
    companion object {
        const val TAG_NAME = "name"
        const val TAG_SURNAME = "surname"
        const val TAG_EMAIL = "email"
        const val TAG_IDENTIFICATION = "identification"
        const val TAG_MOVEMENTS = "movements"
    }
}