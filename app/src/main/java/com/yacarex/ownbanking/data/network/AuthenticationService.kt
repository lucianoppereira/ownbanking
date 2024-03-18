package com.yacarex.ownbanking.data.network

import com.google.firebase.auth.AuthResult
import com.yacarex.ownbanking.data.response.LoginResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationService @Inject constructor(private val firebase: FirebaseClient) {

    suspend fun login(email: String, password: String): LoginResponse = runCatching {
        firebase.auth.signInWithEmailAndPassword(email, password).await()
    }.toLoginResponse()

    suspend fun createAccount(email: String, password: String): AuthResult? {
        return firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun deleteAccount() {
        firebase.auth.currentUser?.delete()
    }

    private suspend fun verifyEmailIsVerified(): Boolean {
        firebase.auth.currentUser?.reload()?.await()
        return firebase.auth.currentUser?.isEmailVerified ?: false
    }

    private fun Result<AuthResult>.toLoginResponse() = when (val result = getOrNull()) {
        null -> LoginResponse.Error
        else -> {
            val userId = result.user
            checkNotNull(userId)
            LoginResponse.Success(result.user?.isEmailVerified ?: false)
        }
    }
}