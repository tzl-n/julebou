package com.example.club

import com.example.club.databinding.FragmentClubBinding
import com.example.club.base.BaseFragment
import com.example.club.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

/** ClubFragment 占位 ViewModel */
class ClubViewModel : BaseViewModel()

@AndroidEntryPoint
class ClubFragment : BaseFragment<FragmentClubBinding, ClubViewModel>(R.layout.fragment_club) {
    override fun initView() {}
}
