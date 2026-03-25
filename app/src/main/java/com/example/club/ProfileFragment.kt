package com.example.club

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        
        // 点击"点击登录"跳转到登录页
        view.findViewById<android.widget.TextView>(R.id.login).setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        
        return view
    }
}
