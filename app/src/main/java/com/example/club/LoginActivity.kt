package com.example.club

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 返回按钮
        findViewById<android.widget.ImageView>(R.id.iv_back).setOnClickListener {
            finish()
        }
    }
}
