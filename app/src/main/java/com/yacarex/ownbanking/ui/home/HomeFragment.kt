package com.yacarex.ownbanking.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yacarex.ownbanking.R
import com.yacarex.ownbanking.databinding.ActivityLoginBinding
import com.yacarex.ownbanking.databinding.FragmentHomeBinding
import com.yacarex.ownbanking.ui.BaseFragment
import com.yacarex.ownbanking.ui.onboarding.utils.Fields
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root
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
}