package com.example.club

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

/**
 * 位置选择器 Activity
 */
class LocationPickerActivity : AppCompatActivity() {
    
    // 热门城市列表
    private val cities = listOf(
        "北京市",
        "上海市",
        "广州市",
        "深圳市",
        "成都市",
        "杭州市",
        "南京市",
        "武汉市",
        "西安市",
        "重庆市"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_picker)
        
        setupListView()
    }
    
    private fun setupListView() {
        val listView = findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            cities
        )
        listView.adapter = adapter
        
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent()
            intent.putExtra("location_name", cities[position])
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
