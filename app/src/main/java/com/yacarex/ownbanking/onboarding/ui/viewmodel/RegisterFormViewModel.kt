package com.yacarex.ownbanking.onboarding.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yacarex.ownbanking.data.RegisterFormData
import com.yacarex.ownbanking.onboarding.ui.fragments.RegisterFormFragment
import com.yacarex.ownbanking.onboarding.ui.utils.FieldValidationListener
import com.yacarex.ownbanking.onboarding.ui.utils.Fields
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterFormViewModel @Inject constructor() : ViewModel(){

    private var validationListener: FieldValidationListener? = null
    private val userForm = RegisterFormData()

    fun registerUser(listener: FieldValidationListener) {
        validationListener = listener
        if (validateForm()) createUser()
    }

    fun validateForm(): Boolean {

        val validations = ArrayList<Boolean>()

        validations.add(verifyName())
        validations.add(verifySurname())
        validations.add(verifyEmail())
        validations.add(verifyPass())

        return !validations.contains(false)
    }

    private fun createUser() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(userForm.email!!, userForm.pass!!)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    validationListener?.fieldValidationSuccess()
                } else {
                    validationListener?.authenticationError()
                }
            }
    }

    fun setName(name: String?) {
        userForm.name = name
    }

    fun setSurname(surname: String?) {
        userForm.surname = surname
    }

    fun setEmail(email: String?) {
        userForm.email = email
    }

    fun setPass(pass: String?) {
        userForm.pass = pass
    }

    fun setRepeatPass(repeatPass: String?) {
        userForm.repeatPass = repeatPass
    }

    private fun verifyName() = !userForm.name.isNullOrBlank().also {
        validationListener?.fieldValidError(Fields.NAME)
    }

    private fun verifySurname() = !userForm.surname.isNullOrBlank().also {
        validationListener?.fieldValidError(Fields.SURNAME)
    }

    private fun verifyEmail(): Boolean {
        userForm.email?.let {
            if (it.matches(RegisterFormFragment.EMAIL_REGEX.toRegex())) {
                return true
            } else {
                validationListener?.fieldValidError(Fields.EMAIL)
                return false
            }
        }

        validationListener?.fieldEmptyError(Fields.EMAIL)
        return false
    }

    private fun verifyPass() = when {
        userForm.pass != userForm.repeatPass -> {
            validationListener?.passInconsistencyError()
            false
        }
        userForm.pass.isNullOrBlank() || userForm.repeatPass.isNullOrBlank()-> {
            validationListener?.fieldEmptyError(Fields.PASS)
            validationListener?.fieldEmptyError(Fields.REPEAT_PASS)
            false
        }
        else -> true
    }
}