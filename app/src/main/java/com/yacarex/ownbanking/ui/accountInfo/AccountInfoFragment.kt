package com.yacarex.ownbanking.ui.accountInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yacarex.ownbanking.databinding.FragmentAccountInfoBinding

class AccountInfoFragment : Fragment() {

    private val binding by lazy {
        FragmentAccountInfoBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accountInfoViewModel = ViewModelProvider(this)[AccountInfoViewModel::class.java]

        return binding.root
    }
}
