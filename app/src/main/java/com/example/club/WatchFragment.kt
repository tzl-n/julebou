package com.example.club

import com.example.club.databinding.FragmentWatchBinding
import com.example.club.base.BaseFragment
import com.example.club.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

/** WatchFragment 占位 ViewModel */
class WatchViewModel : BaseViewModel()

@AndroidEntryPoint
class WatchFragment : BaseFragment<FragmentWatchBinding, WatchViewModel>(R.layout.fragment_watch) {
    override fun initView() {}
}
