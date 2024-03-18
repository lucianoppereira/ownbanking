package com.yacarex.ownbanking.ui.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yacarex.ownbanking.core.event.Event
import com.yacarex.ownbanking.core.utils.Constants
import com.yacarex.ownbanking.core.utils.Constants.EMAIL_REGEX
import com.yacarex.ownbanking.core.utils.Constants.STATE_EMPTY
import com.yacarex.ownbanking.core.utils.Constants.STATE_INVALID_MAIL
import com.yacarex.ownbanking.core.utils.Constants.STATE_INVALID_PASS
import com.yacarex.ownbanking.core.utils.Constants.STATE_VALID
import com.yacarex.ownbanking.data.response.LoginResponse
import com.yacarex.ownbanking.domain.LoginUseCase
import com.yacarex.ownbanking.ui.login.model.LoginData
import com.yacarex.ownbanking.ui.login.states.LoginViewState
import com.yacarex.ownbanking.ui.onboarding.model.SignUpFormData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase) : ViewModel() {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 8
    }

    private val _navigateToHome = MutableLiveData<Event<Boolean>>()
    val navigateToHome: LiveData<Event<Boolean>>
        get() = _navigateToHome

    private val _navigateToSignUp = MutableLiveData<Event<Boolean>>()
    val navigateToSignUp: LiveData<Event<Boolean>>
        get() = _navigateToSignUp

    private val _navigateToVerifyAccount = MutableLiveData<Event<Boolean>>()
    val navigateToVerifyAccount: LiveData<Event<Boolean>>
        get() = _navigateToVerifyAccount

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState>
        get() = _viewState

    private var _showErrorDialog = MutableLiveData(false)
    val showErrorDialog: LiveData<Boolean>
        get() = _showErrorDialog

    fun onLoginSelected(loginData: LoginData) {
        val viewState = loginData.toLoginViewState()
        if (viewState.userValidated()) {
            loginUser(loginData.email, loginData.pass)
        } else {
            updateErrorFields(loginData)
        }
    }

    private fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _viewState.value = LoginViewState(isLoading = true)
            when (val result = loginUseCase(email, password)) {
                is LoginResponse.Error -> {
                    _showErrorDialog.value = true
                    _viewState.value = LoginViewState(isLoading = false)
                }
                is LoginResponse.Success -> {
                    if (result.verified) {
                        _navigateToHome.value = Event(true)
                    } else {
                        _navigateToVerifyAccount.value = Event(true)
                    }
                }
            }
            _viewState.value = LoginViewState(isLoading = false)
        }
    }

    fun onFieldsChanged(loginData: LoginData) {
        _viewState.value = loginData.toLoginViewState(true)
    }

    private fun updateErrorFields(loginData: LoginData) {
        _viewState.value = loginData.toLoginViewState()
    }

    private fun validateEmail(email: String) = when {
        email.matches(EMAIL_REGEX.toRegex()) -> STATE_VALID
        email.isEmpty() -> STATE_EMPTY
        else -> STATE_INVALID_MAIL
    }

    private fun validatePassword(password: String) = when{
        password.length >= Constants.MIN_PASSWORD_LENGTH-> STATE_VALID
        password.isEmpty() -> Constants.STATE_EMPTY
        else -> STATE_INVALID_PASS
    }

    private fun LoginData.toLoginViewState(neutral: Boolean = false): LoginViewState =
        if (neutral) {
            LoginViewState(
                isValidEmail = Constants.STATE_NEUTRAL,
                isValidPassword = Constants.STATE_NEUTRAL,
            )
        } else {
            LoginViewState(
                isValidEmail = validateEmail(email),
                isValidPassword = validatePassword(pass),
            )
        }
}