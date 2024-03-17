package com.yacarex.ownbanking.ui.transferences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yacarex.ownbanking.R
import com.yacarex.ownbanking.databinding.FragmentTransferBinding

class TransferFragment : Fragment() {

    private val binding by lazy {
        FragmentTransferBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}
