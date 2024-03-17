package com.yacarex.ownbanking.onboarding.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yacarex.ownbanking.R
import com.yacarex.ownbanking.databinding.FragmentIdScanBinding

class RegisterIdScanFragment : Fragment() {

    private val binding by lazy {
        FragmentIdScanBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configView(binding)

        return binding.root
    }

    private fun configView(binding: FragmentIdScanBinding) {
        with(this.binding) {
            continueBtn.setOnClickListener {
                findNavController().navigate(R.id.action_registerIdScan_to_registerFinish)
            }
        }
    }
}