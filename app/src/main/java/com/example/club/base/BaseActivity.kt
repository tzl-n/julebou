package com.example.club.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

/**
 * MVVM 基础 Activity
 * - DataBinding 自动绑定
 * - ViewModel 自动实例化（Hilt 注入时用 by viewModels() 覆盖）
 * - 自动观察 UiState 控制 LoadingDialog
 * - 自动观察 toastEvent 弹 Toast
 */
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel>(
    private val layoutId: Int
) : AppCompatActivity() {

    lateinit var binding: VB
        private set

    lateinit var viewModel: VM
        private set

    private val loadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        viewModel = createViewModel()
        observeBase()
        initView()
        observeData()
    }

    @Suppress("UNCHECKED_CAST")
    private fun createViewModel(): VM {
        val type = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VM>
        return ViewModelProvider(this)[type]
    }

    private fun observeBase() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> onShowLoading()
                    is UiState.Success -> onHideLoading()
                    is UiState.Error   -> { onHideLoading(); onShowError(state.message) }
                    is UiState.Idle    -> onHideLoading()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.toastEvent.collectLatest { msg -> showToast(msg) }
        }
    }

    /** 显示 Loading 弹窗（子类可覆盖换自定义弹窗） */
    open fun onShowLoading(message: String = "加载中...") {
        loadingDialog.show(message)
    }

    /** 隐藏 Loading 弹窗 */
    open fun onHideLoading() {
        loadingDialog.safeDismiss()
    }

    /** 显示错误提示（默认弹 Toast，子类可覆盖） */
    open fun onShowError(message: String) {
        showToast(message)
    }

    /** 弹出 Toast */
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /** 初始化视图、绑定点击等（子类必须实现） */
    abstract fun initView()

    /** 观察业务数据（子类按需实现） */
    open fun observeData() {}

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.safeDismiss()
    }
}
