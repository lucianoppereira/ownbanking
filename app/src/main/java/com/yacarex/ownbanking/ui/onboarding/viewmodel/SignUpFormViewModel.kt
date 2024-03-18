package com.yacarex.ownbanking.ui.onboarding.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yacarex.ownbanking.core.event.Event
import com.yacarex.ownbanking.core.utils.Constants.EMAIL_REGEX
import com.yacarex.ownbanking.core.utils.Constants.MIN_PASSWORD_LENGTH
import com.yacarex.ownbanking.core.utils.Constants.STATE_EMPTY
import com.yacarex.ownbanking.core.utils.Constants.STATE_INCONSISTENCY
import com.yacarex.ownbanking.core.utils.Constants.STATE_INVALID_MAIL
import com.yacarex.ownbanking.core.utils.Constants.STATE_INVALID_PASS
import com.yacarex.ownbanking.core.utils.Constants.STATE_NEUTRAL
import com.yacarex.ownbanking.core.utils.Constants.STATE_VALID
import com.yacarex.ownbanking.domain.SignUpUseCase
import com.yacarex.ownbanking.ui.onboarding.model.SignUpFormData
import com.yacarex.ownbanking.ui.onboarding.states.SignUpViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpFormViewModel @Inject constructor(val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _navigateToIdScan = MutableLiveData<Event<Boolean>>()
    val navigateToIdScan: LiveData<Event<Boolean>>
        get() = _navigateToIdScan

    private val _viewState = MutableStateFlow(SignUpViewState())
    val viewState: StateFlow<SignUpViewState>
        get() = _viewState

    private var _showErrorDialog = MutableLiveData(false)
    val showErrorDialog: LiveData<Boolean>
        get() = _showErrorDialog

    fun onContinueSelected(signUpForm: SignUpFormData) {
        val viewState = signUpForm.toSignUpViewState()
        if (viewState.userValidated() && signUpForm.isNotEmpty()) {
            signUpUser(signUpForm)
        } else {
            updateErrorFields(signUpForm)
        }
    }

    private fun signUpUser(signUpForm: SignUpFormData) {
        viewModelScope.launch {
            _viewState.value = SignUpViewState(isLoading = true)
            val accountCreated = signUpUseCase(signUpForm)
            if (accountCreated) {
                _navigateToIdScan.value = Event(true)
            } else {
                _showErrorDialog.value = true
            }
            _viewState.value = SignUpViewState(isLoading = false)
        }
    }

    fun onFieldsChanged(signUpForm: SignUpFormData) {
        _viewState.value = signUpForm.toSignUpViewState(true)
    }

    private fun updateErrorFields(signUpForm: SignUpFormData) {
        _viewState.value = signUpForm.toSignUpViewState()
    }

    private fun validateEmail(email: String) = when {
        email.matches(EMAIL_REGEX.toRegex()) -> STATE_VALID
        email.isEmpty() -> STATE_EMPTY
        else -> STATE_INVALID_MAIL
    }

    private fun validatePassword(password: String, passwordConfirmation: String) = when{

        password.length >= MIN_PASSWORD_LENGTH && password == passwordConfirmation -> {
            STATE_VALID
        }
        password != passwordConfirmation -> STATE_INCONSISTENCY
        password.isEmpty() || passwordConfirmation.isEmpty() -> STATE_EMPTY
        else -> STATE_INVALID_PASS
    }

    private fun validateName(name: String) = when {
        name.isEmpty() -> STATE_EMPTY
        else -> STATE_VALID
    }

    private fun SignUpFormData.toSignUpViewState(neutral: Boolean = false): SignUpViewState =
        if (neutral) {
            SignUpViewState(
                isValidEmail = STATE_NEUTRAL,
                isValidPassword = STATE_NEUTRAL,
                isValidSurname = STATE_NEUTRAL,
                isValidName = STATE_NEUTRAL
            )
        } else {
            SignUpViewState(
                isValidEmail = validateEmail(email),
                isValidPassword = validatePassword(pass, passConfirm),
                isValidSurname = validateName(surname),
                isValidName = validateName(name)
            )
        }
}
