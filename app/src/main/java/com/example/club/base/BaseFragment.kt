package com.example.club.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

/**
 * MVVM 基础 Fragment
 * - DataBinding 安全绑定（onDestroyView 自动置 null）
 * - ViewModel 自动实例化（Hilt 注入时用 by viewModels() 覆盖）
 * - 自动观察 UiState 控制 LoadingDialog
 * - 自动观察 toastEvent 弹 Toast
 */
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    private val layoutId: Int
) : Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    lateinit var viewModel: VM
        private set

    private var loadingDialog: LoadingDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog(requireContext())
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> onShowLoading()
                    is UiState.Success -> onHideLoading()
                    is UiState.Error   -> { onHideLoading(); onShowError(state.message) }
                    is UiState.Idle    -> onHideLoading()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toastEvent.collectLatest { msg -> showToast(msg) }
        }
    }

    /** 显示 Loading 弹窗（子类可覆盖） */
    open fun onShowLoading(message: String = "加载中...") {
        loadingDialog?.show(message)
    }

    /** 隐藏 Loading 弹窗 */
    open fun onHideLoading() {
        loadingDialog?.safeDismiss()
    }

    /** 显示错误提示（默认弹 Toast） */
    open fun onShowError(message: String) {
        showToast(message)
    }

    /** 弹出 Toast */
    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    /** 初始化视图（子类必须实现） */
    abstract fun initView()

    /** 观察业务数据（子类按需实现） */
    open fun observeData() {}

    override fun onDestroyView() {
        super.onDestroyView()
        loadingDialog?.safeDismiss()
        loadingDialog = null
        _binding = null
    }
}
