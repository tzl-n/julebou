package com.example.club.app

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate() {
        super.onCreate()
        Log.d("MyApplication", "========== 应用启动 ==========")
        Log.d("MyApplication", "应用包名：$packageName")
        Log.d("MyApplication", "进程名：${getProcessName()}")
        
        // 设置全局异常捕获
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("MyApplication", "========== 未捕获的异常 ==========")
            Log.e("MyApplication", "线程：${thread.name}")
            Log.e("MyApplication", "异常：${throwable.message}")
            Log.e("MyApplication", "堆栈：${Log.getStackTraceString(throwable)}")
        }
    }
}
