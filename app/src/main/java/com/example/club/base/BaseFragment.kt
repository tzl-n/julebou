package com.example.club.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>
 *
 * 使用方式：
 *   class ClubFragment : BaseFragment<FragmentClubBinding, ClubViewModel>(R.layout.fragment_club)
 */
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    private val layoutId: Int
) : Fragment() {

    private var _binding: VB? = null
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
        initView()
        observeData()
    }

    @Suppress("UNCHECKED_CAST")
    private fun createViewModel(): VM {
        val type = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VM>
        return ViewModelProvider(this)[type]
    }

    abstract fun initView()

    open fun observeData() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
