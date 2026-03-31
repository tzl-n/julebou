package com.example.club

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 输出日志
        println("========== MainActivity 启动 ==========")
        println("应用包名：$packageName")
        println("====================================")

        // 测试 API 连接并显示数据
        testApiAndShowData()

        // 使用协程延迟跳转，避免 Handler 持有 Activity 引用导致内存泄漏
        lifecycleScope.launch {
            delay(3000L)
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        }
    }

    private fun testApiAndShowData() {
        lifecycleScope.launch {
            try {
                println("\n========== API 测试开始 ==========")
                
                // 使用 ApiTestUtil 测试服务器
                val mainServerOk = com.example.club.utils.ApiTestUtil.testMainServer()
                
                println("\n测试结果:")
                println("主服务器：${if (mainServerOk) "✓ 正常" else "✗ 异常"}")
                
                // 测试获取帖子数据
                if (mainServerOk) {
                    println("\n========== 获取帖子数据 ==========")
                    com.example.club.utils.ApiTestUtil.testPostsData()
                }
                
                println("====================================\n")
                
            } catch (e: Exception) {
                println("API 测试异常：${e.message}")
                e.printStackTrace()
            }
        }
    }
}
