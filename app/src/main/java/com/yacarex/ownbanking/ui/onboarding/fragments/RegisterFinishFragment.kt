package com.yacarex.ownbanking.ui.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yacarex.ownbanking.R
import com.yacarex.ownbanking.databinding.FragmentRegisterFinishBinding

class RegisterFinishFragment : Fragment() {

    private val binding by lazy {
        FragmentRegisterFinishBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configView(binding)
    }

    private fun configView(binding: FragmentRegisterFinishBinding) {
        with(this.binding) {
            continueBtn.setOnClickListener {
                findNavController().navigate(R.id.action_registerFinish_to_home)
            }
        }
    }
}