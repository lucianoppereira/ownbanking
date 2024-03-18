package com.yacarex.ownbanking.ui.onboarding.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yacarex.ownbanking.core.event.Event
import com.yacarex.ownbanking.domain.SignUpUseCase
import com.yacarex.ownbanking.ui.onboarding.model.SignUpFormData
import com.yacarex.ownbanking.ui.onboarding.states.SignUpViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpIdScanViewModel @Inject constructor(val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _navigateToSignUpSuccess = MutableLiveData<Event<Boolean>>()
    val navigateToSignUpSuccess: LiveData<Event<Boolean>>
        get() = _navigateToSignUpSuccess

    private val _viewState = MutableStateFlow(SignUpViewState())
    val viewState: StateFlow<SignUpViewState>
        get() = _viewState

    private var _showErrorDialog = MutableLiveData(false)
    val showErrorDialog: LiveData<Boolean>
        get() = _showErrorDialog


    fun onScanIdSelected() {

    }

    fun onContinueSelected() {
        //if () {
        //    continue()
        //} else {
        //    updateErrorFields()
        //}
    }

    private fun uploadPhoto(signUpForm: SignUpFormData) {
        viewModelScope.launch {
            _viewState.value = SignUpViewState(isLoading = true)
            val accountCreated = signUpUseCase(signUpForm)
            if (accountCreated) {
                _navigateToSignUpSuccess.value = Event(true)
            } else {
                _showErrorDialog.value = true
            }
            _viewState.value = SignUpViewState(isLoading = false)
        }
    }
}