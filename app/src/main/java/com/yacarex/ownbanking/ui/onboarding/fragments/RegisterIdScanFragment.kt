package com.yacarex.ownbanking.ui.onboarding.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yacarex.ownbanking.R
import com.yacarex.ownbanking.databinding.FragmentIdScanBinding
import com.yacarex.ownbanking.ui.BaseFragment
import com.yacarex.ownbanking.ui.login.model.LoginData
import com.yacarex.ownbanking.ui.onboarding.utils.Fields
import com.yacarex.ownbanking.ui.onboarding.viewmodel.SignUpIdScanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterIdScanFragment : BaseFragment<FragmentIdScanBinding>(
    FragmentIdScanBinding::inflate
) {

    private val viewModel: SignUpIdScanViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            registerIdScan.takePhotoBtn.setOnClickListener {
                findNavController().navigate(R.id.action_scanIdFragment_to_cameraFragment)
            }

            continueBtn.setOnClickListener {
                viewModel.onContinueSelected(

                )
            }

        }
    }

    private fun initObservers() {
        viewModel.navigateToSignUpSuccess.observe(requireActivity()) {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.action_registerIdScan_to_registerFinish)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { viewState ->
                //updateUI(viewState)
            }
        }

        viewModel.showErrorDialog.observe(requireActivity()) { showError ->
            if (showError) showErrorDialog()
        }
    }

    private fun configView(binding: FragmentIdScanBinding) {
        with(this.binding) {
            continueBtn.setOnClickListener {
                findNavController().navigate(R.id.action_registerIdScan_to_registerFinish)
            }
        }
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.title_error_dialog))
        builder.setMessage(getString(R.string.description_photo_error))
        builder.setPositiveButton(getString(R.string.button_ok), null)

        val dialog = builder.create()
        dialog.show()
    }

    override fun fieldValidationSuccess() {
        TODO("Not yet implemented")
    }

    override fun fieldEmptyError(field: Fields) {
        TODO("Not yet implemented")
    }

    override fun fieldValidError(field: Fields) {
        TODO("Not yet implemented")
    }

    override fun passInconsistencyError() {
        TODO("Not yet implemented")
    }

    override fun authenticationError() {
        TODO("Not yet implemented")
    }
}