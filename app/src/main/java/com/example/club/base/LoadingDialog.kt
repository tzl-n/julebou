package com.example.club.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import com.example.club.R

/**
 * 通用加载弹窗
 * 使用：
 * ```kotlin
 * val dialog = LoadingDialog(context)
 * dialog.show("加载中...")
 * dialog.dismiss()
 * ```
 */
class LoadingDialog(context: Context) : Dialog(context, R.style.LoadingDialogStyle) {

    private var tvMessage: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 使用代码创建视图，避免依赖额外布局文件
        val layout = android.widget.LinearLayout(context).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(60, 60, 60, 60)
            val bg = android.graphics.drawable.GradientDrawable().apply {
                setColor(Color.WHITE)
                cornerRadius = 24f
            }
            background = bg
        }

        val progressBar = ProgressBar(context).apply {
            isIndeterminate = true
        }

        tvMessage = TextView(context).apply {
            text = "加载中..."
            textSize = 14f
            setTextColor(Color.parseColor("#333333"))
            val lp = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = 20 }
            layoutParams = lp
        }

        layout.addView(progressBar)
        layout.addView(tvMessage)
        setContentView(layout)

        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val params = attributes
            params.width = WindowManager.LayoutParams.WRAP_CONTENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            attributes = params
        }
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    /** 显示弹窗并设置提示文字 */
    fun show(message: String = "加载中...") {
        if (!isShowing) {
            tvMessage?.text = message
            show()
        }
    }

    /** 安全关闭弹窗（防止 Activity 已销毁时崩溃） */
    fun safeDismiss() {
        try {
            if (isShowing) dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
