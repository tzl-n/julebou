package com.example.club

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }
    }
}
