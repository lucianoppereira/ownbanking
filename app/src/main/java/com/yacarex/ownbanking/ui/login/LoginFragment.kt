package com.yacarex.ownbanking.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.yacarex.ownbanking.R
import com.yacarex.ownbanking.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configView(binding)

        return binding.root
    }

    private fun configView(binding: FragmentLoginBinding) {
        with(binding) {
            userEditText.doAfterTextChanged {

            }

            loginInit(loginBtn)
            registerInit(registerBtn)
        }
    }

    private fun loginInit(loginBtn: MaterialButton) {
        loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_home)
        }
    }

    private fun registerInit(registerBtn: MaterialButton) {
        registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register_form)
        }
    }
}
