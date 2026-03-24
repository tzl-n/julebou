package com.example.club

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    
    private lateinit var bottomNavigationView: BottomNavigationView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        
        // 设置默认 Fragment
        if (savedInstanceState == null) {
            loadFragment(ClubFragment())
        }
        
        // 设置底部导航栏点击监听
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_club -> {
                    loadFragment(ClubFragment())
                    true
                }
                R.id.navigation_play -> {
                    loadFragment(PlayFragment())
                    true
                }
                R.id.navigation_watch -> {
                    loadFragment(WatchFragment())
                    true
                }
                R.id.navigation_message -> {
                    loadFragment(MessageFragment())
                    true
                }
                R.id.navigation_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
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
