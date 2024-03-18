package com.yacarex.ownbanking.core.extensions

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.yacarex.ownbanking.core.dialog.DialogFragmentLauncher

fun DialogFragment.show(launcher: DialogFragmentLauncher, activity: FragmentActivity) {
    launcher.show(this, activity)
}