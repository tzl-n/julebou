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
 *
 * 自动完成：
 * - DataBinding 绑定（安全 _binding 模式，onDestroyView 自动置 null）
 * - ViewModel 实例化（反射，Hilt 注入用 by viewModels() 覆盖）
 * - 观察 [UiState] 和 [toastEvent]
 *
 * 使用：
 * ```kotlin
 * @AndroidEntryPoint
 * class ClubFragment : BaseFragment<FragmentClubBinding, ClubViewModel>(R.layout.fragment_club) {
 *     override fun initView() { binding.vm = viewModel }
 * }
 * ```
 */
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    private val layoutId: Int
) : Fragment() {

    private var _binding: VB? = null

    /** 安全访问 binding（仅在 onCreateView ~ onDestroyView 之间有效） */
    val binding: VB get() = _binding!!

    lateinit var viewModel: VM
        private set

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
        viewLifecycleOwner.lifecycleScope.launch {
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toastEvent.collectLatest { msg ->
                showToast(msg)
            }
        }
    }

    /** 显示 Loading（子类可覆盖替换为自定义弹窗） */
    open fun onShowLoading() {}

    /** 隐藏 Loading */
    open fun onHideLoading() {}

    /** 显示错误（默认弹 Toast） */
    open fun onShowError(message: String) {
        showToast(message)
    }

    /** 弹出 Toast */
    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    /** 初始化视图、绑定点击事件等 */
    abstract fun initView()

    /** 观察业务 LiveData / StateFlow */
    open fun observeData() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
