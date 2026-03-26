package com.example.club

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.club.databinding.ActivityHomeBinding
import com.example.club.base.BaseActivity
import com.example.club.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/** HomeActivity 的占位 ViewModel（后续替换为真实业务 ViewModel）*/
class HomeViewModel : BaseViewModel()

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {

    override fun initView() {
        // 设置默认 Fragment
        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            loadFragment(ClubFragment())
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_club    -> { loadFragment(ClubFragment());    true }
                R.id.navigation_play    -> { loadFragment(PlayFragment());    true }
                R.id.navigation_watch   -> { loadFragment(WatchFragment());   true }
                R.id.navigation_message -> { loadFragment(MessageFragment()); true }
                R.id.navigation_profile -> { loadFragment(ProfileFragment()); true }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
