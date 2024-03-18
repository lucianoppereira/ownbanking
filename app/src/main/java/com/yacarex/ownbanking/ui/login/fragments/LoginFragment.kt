package com.yacarex.ownbanking.ui.login.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yacarex.ownbanking.R
import com.yacarex.ownbanking.core.dialog.DialogFragmentLauncher
import com.yacarex.ownbanking.core.utils.Constants.STATE_EMPTY
import com.yacarex.ownbanking.core.utils.Constants.STATE_INVALID_MAIL
import com.yacarex.ownbanking.core.utils.Constants.STATE_INVALID_PASS
import com.yacarex.ownbanking.databinding.FragmentLoginBinding
import com.yacarex.ownbanking.ui.BaseFragment
import com.yacarex.ownbanking.ui.login.model.LoginData
import com.yacarex.ownbanking.ui.login.states.LoginViewState
import com.yacarex.ownbanking.ui.login.viewModel.LoginViewModel
import com.yacarex.ownbanking.ui.onboarding.states.SignUpViewState
import com.yacarex.ownbanking.ui.onboarding.utils.Fields
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun fieldValidationSuccess() {

    }

    override fun fieldEmptyError(field: Fields) {

    }

    override fun fieldValidError(field: Fields) {

    }

    override fun passInconsistencyError() {

    }

    override fun authenticationError() {

    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            emailET.onEditorAction(EditorInfo.IME_ACTION_NEXT)
            emailET.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
            emailET.doOnTextChanged { _, _, _, _ -> onFieldChanged() }

            passTV.onEditorAction(EditorInfo.IME_ACTION_DONE)
            passTV.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
            passTV.doOnTextChanged { _, _, _, _ -> onFieldChanged() }

            loginBtn.setOnClickListener {
                viewModel.onLoginSelected(
                    LoginData(
                        emailET.text.toString(),
                        passTV.text.toString()
                    )
                )
            }

            registerBtn.setOnClickListener {
                findNavController().navigate(R.id.action_login_to_register_form)
            }
        }
    }

    private fun initObservers() {
        viewModel.navigateToHome.observe(requireActivity()) {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.action_login_to_home)
            }
        }

        viewModel.navigateToSignUp.observe(requireActivity()) {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.action_login_to_register_form)
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

    private fun updateUI(viewState: LoginViewState) {
        with(binding) {
            loading.loadingScreen.isVisible = viewState.isLoading
            emailTF.error = getErrorMessage(viewState.isValidEmail)
            passTextField.error = getErrorMessage(viewState.isValidPassword)
        }
    }

    private fun getErrorMessage(state: String) = when(state) {
        STATE_INVALID_MAIL -> getString(R.string.error_field_email_invalid)
        STATE_INVALID_PASS -> getString(R.string.error_field_pass_invalid)
        STATE_EMPTY -> getString(R.string.error_field_empty)
        else -> ""
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.title_error_dialog))
        builder.setMessage(getString(R.string.description_authentication_error))
        builder.setPositiveButton(getString(R.string.button_ok), null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun onFieldChanged(hasFocus: Boolean = false) {
        with(binding) {
            if (!hasFocus) {
                viewModel.onFieldsChanged(
                    LoginData(
                        email = emailET.text.toString(),
                        pass = passTV.text.toString()
                    )
                )
            }
        }
    }
}
