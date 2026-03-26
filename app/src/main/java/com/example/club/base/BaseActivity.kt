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
 *
 * 自动完成：
 * - DataBinding 绑定
 * - ViewModel 实例化（反射，Hilt 注入用 by viewModels() 覆盖）
 * - 观察 [UiState]（Loading / Success / Error）
 * - 观察 [toastEvent] 自动弹 Toast
 *
 * 使用：
 * ```kotlin
 * @AndroidEntryPoint
 * class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {
 *     override fun initView() { binding.vm = viewModel }
 * }
 * ```
 */
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel>(
    private val layoutId: Int
) : AppCompatActivity() {

    lateinit var binding: VB
        private set

    lateinit var viewModel: VM
        private set

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

    /** 观察基础 UiState 和 Toast 事件 */
    private fun observeBase() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> onShowLoading()
                    is UiState.Success -> onHideLoading()
                    is UiState.Error   -> {
                        onHideLoading()
                        onShowError(state.message)
                    }
                    is UiState.Idle    -> onHideLoading()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.toastEvent.collectLatest { msg ->
                showToast(msg)
            }
        }
    }

    /** 显示 Loading（子类可覆盖替换为自定义 Loading 弹窗） */
    open fun onShowLoading() {}

    /** 隐藏 Loading */
    open fun onHideLoading() {}

    /** 显示错误（默认弹 Toast，子类可覆盖） */
    open fun onShowError(message: String) {
        showToast(message)
    }

    /** 弹出 Toast */
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /** 初始化视图、绑定点击事件等 */
    abstract fun initView()

    /** 观察业务 LiveData / StateFlow（子类按需实现） */
    open fun observeData() {}
}
