package com.yacarex.ownbanking.data.network

import com.yacarex.ownbanking.ui.onboarding.model.AccountInfo
import com.yacarex.ownbanking.ui.onboarding.model.AccountInfo.Companion.TAG_EMAIL
import com.yacarex.ownbanking.ui.onboarding.model.AccountInfo.Companion.TAG_NAME
import com.yacarex.ownbanking.ui.onboarding.model.AccountInfo.Companion.TAG_SURNAME
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserService @Inject constructor(private val firebase: FirebaseClient) {

    companion object {
        const val USER_COLLECTION = "users"
    }

    suspend fun createUserTable(accountInfo: AccountInfo) = runCatching {

        val user = hashMapOf(
            TAG_EMAIL to accountInfo.userEmail,
            TAG_NAME to accountInfo.userName,
            TAG_SURNAME to accountInfo.userSurname
        )

        firebase.db
            .collection(USER_COLLECTION)
            .add(user).await()

    }.isSuccess
}