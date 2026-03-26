package com.example.club

import com.example.club.databinding.FragmentMessageBinding
import com.example.club.base.BaseFragment
import com.example.club.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

/** MessageFragment 占位 ViewModel */
class MessageViewModel : BaseViewModel()

@AndroidEntryPoint
class MessageFragment : BaseFragment<FragmentMessageBinding, MessageViewModel>(R.layout.fragment_message) {
    override fun initView() {}
}
