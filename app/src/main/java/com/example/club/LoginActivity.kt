package com.example.club

import com.example.club.databinding.ActivityLoginBinding
import com.example.club.base.BaseActivity
import com.example.club.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

/** LoginActivity 的占位 ViewModel */
class LoginViewModel : BaseViewModel()

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    override fun initView() {
        binding.ivBack.setOnClickListener { finish() }
    }
}
