package com.example.club

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.club.adapter.PostsAdapter
import com.example.club.databinding.FragmentClubBinding
import com.example.club.base.BaseFragment
import com.example.club.base.BaseViewModel
import com.example.club.network.model.PostsModel
import com.example.club.network.service.PostsApiService
import com.example.club.utils.ApiTestUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** ClubFragment 占位 ViewModel */
class ClubViewModel : BaseViewModel() {

    private val _posts = MutableStateFlow<List<PostsModel>>(emptyList())
    val posts: StateFlow<List<PostsModel>> = _posts.asStateFlow()

    fun loadPosts(postsApiService: PostsApiService, category: Int = 1, pageNum: Int = 1, pageSize: Int = 10) {
        Log.d("ClubViewModel", "开始加载帖子数据...")
        Log.d("ClubViewModel", "API: ${com.example.club.network.api.ApiConstants.Posts.PAGE}")
        Log.d("ClubViewModel", "参数：category=$category, pageNum=$pageNum, pageSize=$pageSize")
        
        request(
            call = { postsApiService.getPostsPage(category, null, null, null, null, null, pageNum, pageSize) },
            onSuccess = { pageModel ->
                Log.d("ClubViewModel", "请求成功")
                Log.d("ClubViewModel", "总记录数：${pageModel.total}")
                Log.d("ClubViewModel", "原始记录数：${pageModel.records.size}")
                Log.d("ClubViewModel", "records 类型：${pageModel.records::class.java.simpleName}")
                if (pageModel.records.isNotEmpty()) {
                    Log.d("ClubViewModel", "第一条记录类型：${pageModel.records[0]::class.java.simpleName}")
                    Log.d("ClubViewModel", "第一条记录内容：${pageModel.records[0]}")
                }
                
                // 从 BasePageModel 中提取 records 数据
                @Suppress("UNCHECKED_CAST")
                val records = pageModel.records as? List<PostsModel> ?: emptyList()
                Log.d("ClubViewModel", "转换后的数据：${records.size} 条")
                Log.d("ClubViewModel", "转换后列表类型：${records::class.java.simpleName}")
                if (records.isNotEmpty()) {
                    Log.d("ClubViewModel", "转换后第一条类型：${records[0]::class.java.simpleName}")
                    Log.d("ClubViewModel", "转换后第一条内容：${records[0].content}")
                } else {
                    Log.e("ClubViewModel", "⚠️ 转换后数据为空！原始数据：${pageModel.records.size} 条")
                    Log.e("ClubViewModel", "原始数据第一条：${pageModel.records.firstOrNull()}")
                }
                
                records.forEachIndexed { index, post ->
                    Log.d("ClubViewModel", "[$index] 帖子：${post.content.take(50)}...")
                }
                
                Log.d("ClubViewModel", "准备更新 StateFlow，数据量：${records.size}")
                _posts.value = records
                Log.d("ClubViewModel", "StateFlow 已更新")
            },
            onError = { errorMsg ->
                Log.e("ClubViewModel", "请求失败：$errorMsg")
            }
        )
    }
}

@AndroidEntryPoint
class ClubFragment : BaseFragment<FragmentClubBinding, ClubViewModel>(R.layout.fragment_club) {
    
    // 当前位置信息
    private var currentLocation = "北京市"
    
    // 帖子列表 Adapter
    private val postsAdapter = PostsAdapter()
    
    // 分页参数
    private var currentPage = 1
    private val pageSize = 10
    
    @Inject
    lateinit var postsApiService: PostsApiService
    
    // 位置选择器启动器
    private val locationPickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val locationName = result.data?.getStringExtra("location_name")
            locationName?.let {
                currentLocation = it
                binding.tvLocation.text = it
                Toast.makeText(requireContext(), "已切换到 $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initView() {
        Log.d("ClubFragment", "========== 初始化视图 ==========")
        
        // 初始化头部标题栏
        setupHeader()
        
        // 先设置数据观察者（必须在加载数据之前）
        setupDataObserver()
        
        // 初始化 RecyclerView
        setupRecyclerView()
        
        // 初始化下拉刷新
        setupSwipeRefresh()
        
        // 直接加载数据（移除测试 API 连接）
        viewModel.loadPosts(postsApiService)
    }
    
    /**
     * 设置头部标题栏
     */
    private fun setupHeader() {
        // 显示当前位置
        binding.tvLocation.text = currentLocation
        
        // 点击定位切换位置
        binding.locationLayout.setOnClickListener {
            showLocationPicker()
        }
        
        // 搜索图标点击事件
        binding.ivSearch.setOnClickListener {
            Toast.makeText(requireContext(), "点击搜索", Toast.LENGTH_SHORT).show()
        }
        
        // 添加图标点击事件
        binding.ivAdd.setOnClickListener {
            Toast.makeText(requireContext(), "点击添加", Toast.LENGTH_SHORT).show()
        }
    }
    
    /**
     * 显示位置选择器
     */
    private fun showLocationPicker() {
        val intent = Intent(requireContext(), LocationPickerActivity::class.java)
        locationPickerLauncher.launch(intent)
    }
    
    /**
     * 设置 RecyclerView
     */
    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postsAdapter
        }
        
        // 监听点赞点击
        postsAdapter.onItemClickListener = object : PostsAdapter.OnItemClickListener {
            override fun onLikeClick(post: PostsModel, isLiked: Boolean) {
                Toast.makeText(
                    context,
                    if (isLiked) "已点赞" else "已取消点赞",
                    Toast.LENGTH_SHORT
                ).show()
                // TODO: 调用 API 更新点赞状态
            }
        }
    }
    
    /**
     * 设置数据观察者
     */
    private fun setupDataObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.posts.collect { posts ->
                Log.d("ClubFragment", "观察到数据变化：${posts.size} 条")
                postsAdapter.submitList(posts)
                binding.emptyView.visibility = if (posts.isEmpty()) View.VISIBLE else View.GONE
                
                // 如果是刷新状态，停止刷新动画
                if (binding.swipeRefresh.isRefreshing) {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }
    
    /**
     * 设置下拉刷新
     */
    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            Log.d("ClubFragment", "用户下拉刷新")
            currentPage = 1
            viewModel.loadPosts(postsApiService)
        }
        
        // 移除重复的数据观察设置
    }
    
    /**
     * 测试 API 连接 - 仅用于调试，生产环境应移除
     */
    @Suppress("UNUSED_PARAMETER")
    private fun testApiConnection() {
        // 已移除：不需要在每次启动时测试 API 连接
        // 如果需要测试，请手动运行单元测试
    }
}
