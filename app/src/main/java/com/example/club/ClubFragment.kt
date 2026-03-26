package com.example.club

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.club.databinding.FragmentClubBinding

class ClubFragment : Fragment() {

    private var _binding: FragmentClubBinding? = null
    private val binding get() = _binding!!
    
    // 当前位置信息
    private var currentLocation = "北京市"
    
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClubBinding.inflate(inflater, container, false)
        
        // 初始化头部标题栏
        setupHeader()
        
        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




