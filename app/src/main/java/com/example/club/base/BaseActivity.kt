package com.example.club.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * BaseActivity<VB : ViewDataBinding, VM : BaseViewModel>
 *
 * 使用方式：
 *   class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home)
 *
 * DataBinding 变量名约定：如需双向绑定，在 XML 中声明 <variable name="vm" type="...ViewModel" />
 * 并在子类 initView() 后调用 binding.vm = viewModel
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
        initView()
        observeData()
    }

    /** 通过反射自动实例化泛型 ViewModel，Hilt 注入的 ViewModel 请使用 by viewModels() 覆盖此逻辑 */
    @Suppress("UNCHECKED_CAST")
    private fun createViewModel(): VM {
        val type = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VM>
        return ViewModelProvider(this)[type]
    }

    /** 初始化视图、点击事件等 */
    abstract fun initView()

    /** 观察 LiveData / StateFlow */
    open fun observeData() {}
}
