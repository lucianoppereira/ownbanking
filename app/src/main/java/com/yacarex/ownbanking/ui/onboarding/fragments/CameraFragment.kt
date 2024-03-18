package com.yacarex.ownbanking.ui.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yacarex.ownbanking.R
import com.yacarex.ownbanking.databinding.FragmentCameraBinding
import com.yacarex.ownbanking.ui.BaseFragment
import com.yacarex.ownbanking.ui.onboarding.utils.Fields

class CameraFragment : BaseFragment<FragmentCameraBinding>(
    FragmentCameraBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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