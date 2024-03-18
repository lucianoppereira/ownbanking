package com.yacarex.ownbanking.ui.onboarding.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.yacarex.ownbanking.R
import com.yacarex.ownbanking.databinding.FragmentRegisterFormBinding
import com.yacarex.ownbanking.ui.BaseFragment
import com.yacarex.ownbanking.ui.onboarding.model.SignUpFormData
import com.yacarex.ownbanking.ui.onboarding.states.SignUpViewState
import com.yacarex.ownbanking.core.utils.Constants.STATE_EMPTY
import com.yacarex.ownbanking.core.utils.Constants.STATE_INCONSISTENCY
import com.yacarex.ownbanking.core.utils.Constants.STATE_INVALID_MAIL
import com.yacarex.ownbanking.core.utils.Constants.STATE_INVALID_PASS
import com.yacarex.ownbanking.ui.onboarding.utils.Fields
import com.yacarex.ownbanking.ui.onboarding.utils.Fields.PASS_CONFIRM
import com.yacarex.ownbanking.ui.onboarding.viewmodel.SignUpFormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFormFragment : BaseFragment<FragmentRegisterFormBinding>(
    FragmentRegisterFormBinding::inflate
) {

    private val viewModel: SignUpFormViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        configRegisterForm()
        initObservers()
    }

    private fun configRegisterForm() {
        with(binding.registerForm) {
            setInputListener(emailTV)
            setInputListener(nameTV)
            setInputListener(surnameTV)
            setInputListener(passTV)
            setInputListener(passConfirmTV, PASS_CONFIRM)

            binding.continueBtn.setOnClickListener {
                viewModel.onContinueSelected(
                    SignUpFormData(
                        name = nameTV.text.toString(),
                        surname = surnameTV.text.toString(),
                        email = emailTV.text.toString(),
                        pass = passTV.text.toString(),
                        passConfirm = passConfirmTV.text.toString()
                    )
                )
            }
        }
    }

    private fun initObservers() {
        viewModel.navigateToIdScan.observe(requireActivity()) {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.action_registerForm_to_registerIdScan)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }

        viewModel.showErrorDialog.observe(requireActivity()) { showError ->
            if (showError) showErrorDialog()
        }
    }

    private fun setInputListener(input: TextInputEditText, field: Fields? = null) {
        input.apply {
            if (field == PASS_CONFIRM) {
                onEditorAction(EditorInfo.IME_ACTION_DONE)
            } else {
                onEditorAction(EditorInfo.IME_ACTION_NEXT)
            }

            setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
            doOnTextChanged { _ , _, _, _ -> onFieldChanged() }
        }
    }

    private fun updateUI(viewState: SignUpViewState) {
        with(binding.registerForm) {
            binding.loading.loadingScreen.isVisible = viewState.isLoading
            emailTextField.error = getErrorMessage(viewState.isValidEmail)
            surnameTextField.error = getErrorMessage(viewState.isValidSurname)
            nameTextField.error = getErrorMessage(viewState.isValidName)
            passTextField.error = getErrorMessage(viewState.isValidPassword)
            passConfirmTextField.error = getErrorMessage(viewState.isValidPassword)
        }
    }

    private fun getErrorMessage(state: String) = when(state) {
        STATE_INVALID_MAIL -> getString(R.string.error_field_email_invalid)
        STATE_INVALID_PASS -> getString(R.string.error_field_pass_invalid)
        STATE_EMPTY -> getString(R.string.error_field_empty)
        STATE_INCONSISTENCY -> getString(R.string.error_field_inconsistency)
        else -> ""
    }

    private fun onFieldChanged(hasFocus: Boolean = false) {
        with(binding.registerForm) {
            if (!hasFocus) {
                viewModel.onFieldsChanged(
                    SignUpFormData(
                        name = nameTV.text.toString(),
                        surname = surnameTV.text.toString(),
                        email = emailTV.text.toString(),
                        pass = passTV.text.toString(),
                        passConfirm = passConfirmTV.text.toString()
                    )
                )
            }
        }
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.title_error_dialog))
        builder.setMessage(getString(R.string.description_authentication_error))
        builder.setPositiveButton(getString(R.string.button_ok), null)

        val dialog = builder.create()
        dialog.show()
    }

    override fun fieldValidationSuccess() {
        findNavController().navigate(R.id.action_registerForm_to_registerIdScan)
    }

    override fun fieldEmptyError(field: Fields) {
        //getField(field).error = getString(R.string.error_field_empty)
    }

    override fun fieldValidError(field: Fields) {
        //getField(field).error = getString(R.string.error_field_invalid)
    }

    override fun passInconsistencyError() {
        //with(binding.registerForm) {
        //    passwordTextField.error = getString(R.string.error_field_inconsistence)
        //    repeatPassTextField.error = getString(R.string.error_field_inconsistence)
        //}
    }

    override fun authenticationError() {

    }
}