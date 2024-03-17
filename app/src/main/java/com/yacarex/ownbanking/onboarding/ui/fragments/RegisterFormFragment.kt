package com.yacarex.ownbanking.onboarding.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.yacarex.ownbanking.R
import com.yacarex.ownbanking.databinding.FragmentRegisterFormBinding
import com.yacarex.ownbanking.onboarding.ui.utils.Fields
import com.yacarex.ownbanking.onboarding.ui.utils.Fields.NAME
import com.yacarex.ownbanking.onboarding.ui.utils.Fields.SURNAME
import com.yacarex.ownbanking.onboarding.ui.utils.Fields.EMAIL
import com.yacarex.ownbanking.onboarding.ui.utils.Fields.PASS
import com.yacarex.ownbanking.onboarding.ui.utils.Fields.REPEAT_PASS
import com.yacarex.ownbanking.onboarding.ui.viewmodel.RegisterFormViewModel
import com.yacarex.ownbanking.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFormFragment : BaseFragment<FragmentRegisterFormBinding>(
    FragmentRegisterFormBinding::inflate
) {

    private val db = FirebaseFirestore.getInstance()
    private val viewModel: RegisterFormViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configView()
    }

    private fun configView() {
        configRegisterForm()
        binding.continueBtn.setOnClickListener {
            viewModel.registerUser(this)
        }
    }

    private fun configRegisterForm() {
        with(binding.registerForm) {

            nameTV.doAfterTextChanged { text -> viewModel.setName(text?.toString()) }
            nameTV.doOnTextChanged { text , _, _, _ ->
                nameTextField.error = ""
                viewModel.setName(text?.toString())
            }

            surnameTV.doAfterTextChanged { text -> viewModel.setSurname(text?.toString()) }
            surnameTV.doOnTextChanged { text, _, _, _ ->
                surnameTextField.error = ""
                viewModel.setSurname(text?.toString())
            }

            emailTV.doAfterTextChanged { text -> viewModel.setEmail(text?.toString()) }
            emailTV.doOnTextChanged { text, _, _, _ ->
                emailTextField.error = ""
                viewModel.setEmail(text?.toString())
            }

            passwordTV.doAfterTextChanged { text -> viewModel.setPass(text?.toString()) }
            passwordTV.doOnTextChanged { text, _, _, _ ->
                passwordTextField.error = ""
                viewModel.setPass(text?.toString())
            }

            repeatPassTV.doAfterTextChanged { text -> viewModel.setRepeatPass(text?.toString()) }
            repeatPassTV.doOnTextChanged { text, _, _, _ ->
                repeatPassTextField.error = ""
                viewModel.setRepeatPass(text?.toString())
            }
        }
    }

    override fun fieldValidationSuccess() {
        findNavController().navigate(R.id.action_registerForm_to_registerIdScan)
    }

    override fun fieldEmptyError(field: Fields) {
        getField(field).error = getString(R.string.error_field_empty)
    }

    override fun fieldValidError(field: Fields) {
        getField(field).error = getString(R.string.error_field_invalid)
    }

    override fun passInconsistencyError() {
        with(binding.registerForm) {
            passwordTextField.error = getString(R.string.error_field_inconsistence)
            repeatPassTextField.error = getString(R.string.error_field_inconsistence)
        }
    }

    override fun authenticationError() {
        showAlert()
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al autenticar al usuario")
        builder.setPositiveButton("Aceptar", null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun getField(field: Fields) =
        with(binding.registerForm){
            when (field) {
                NAME -> nameTextField
                SURNAME -> surnameTextField
                EMAIL-> emailTextField
                PASS-> passwordTextField
                REPEAT_PASS-> repeatPassTextField
            }
        }

    companion object {
        const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        const val KEY_USERS = "users"
    }
}