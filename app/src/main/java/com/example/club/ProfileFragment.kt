package com.example.club

import android.content.Intent
import android.widget.TextView
import com.example.club.base.BaseFragment
import com.example.club.base.BaseViewModel
import com.example.club.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

/** ProfileFragment 占位 ViewModel */
class ProfileViewModel : BaseViewModel()

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override fun initView() {
        // profile_header 作为 include，login 在 include 内部，需通过根视图查找
        binding.root.findViewById<TextView>(R.id.login)?.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }
}
