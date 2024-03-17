package com.yacarex.ownbanking.login.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yacarex.ownbanking.R
import com.yacarex.ownbanking.databinding.ActivityLoginBinding
import com.yacarex.ownbanking.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}