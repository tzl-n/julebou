package com.example.club

import com.example.club.databinding.FragmentPlayBinding
import com.example.club.base.BaseFragment
import com.example.club.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

/** PlayFragment 占位 ViewModel */
class PlayViewModel : BaseViewModel()

@AndroidEntryPoint
class PlayFragment : BaseFragment<FragmentPlayBinding, PlayViewModel>(R.layout.fragment_play) {
    override fun initView() {}
}
