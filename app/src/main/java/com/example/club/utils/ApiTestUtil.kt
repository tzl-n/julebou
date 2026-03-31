package com.example.club.utils

import android.util.Log
import com.example.club.network.api.ApiConstants
import com.example.club.network.model.PostsModel
import com.example.club.network.service.PostsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * API 测试工具类
 * 用于测试服务器连接和数据返回
 */
object ApiTestUtil {

    private const val TAG = "ApiTestUtil"

    /**
     * 测试主服务器连接
     */
    suspend fun testMainServer(): Boolean = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "========== 测试主服务器连接 ==========")
            Log.d(TAG, "BASE_URL: ${ApiConstants.BASE_URL}")
            
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(PostsApiService::class.java)
            val response = service.getPostsPage(category = 1, pageNum = 1, pageSize = 5)
            
            Log.d(TAG, "响应码：${response.code}")
            Log.d(TAG, "响应消息：${response.msg}")
            Log.d(TAG, "响应数据：${response.data}")
            
            if (response.code == 200) {
                Log.d(TAG, "✓ 主服务器连接成功")
                true
            } else {
                Log.e(TAG, "✗ 主服务器返回错误码：${response.code}")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "✗ 主服务器连接失败", e)
            false
        }
    }

    /**
     * 测试获取帖子数据并打印详情
     */
    suspend fun testPostsData() = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "\n========== 测试帖子数据 ==========")
            
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(PostsApiService::class.java)
            val response = service.getPostsPage(
                category = 1,
                pageNum = 1,
                pageSize = 10
            )
            
            Log.d(TAG, "响应码：${response.code}")
            Log.d(TAG, "响应消息：${response.msg}")
            Log.d(TAG, "是否有数据：${response.data != null}")
            
            response.data?.let { pageModel ->
                Log.d(TAG, "总记录数：${pageModel.total}")
                Log.d(TAG, "总页数：${pageModel.pages}")
                Log.d(TAG, "当前页：${pageModel.current}")
                Log.d(TAG, "每页大小：${pageModel.size}")
                Log.d(TAG, "记录列表：${pageModel.records.size} 条")
                
                pageModel.records.forEachIndexed { index, record ->
                    Log.d(TAG, "\n--- 第 ${index + 1} 条记录 ---")
                    Log.d(TAG, "类型：${record::class.java.simpleName}")
                    Log.d(TAG, "内容：$record")
                    
                    // 尝试转换为 PostsModel
                    if (record is PostsModel) {
                        Log.d(TAG, "帖子 ID: ${record.postsId}")
                        Log.d(TAG, "内容：${record.content}")
                        Log.d(TAG, "昵称：${record.nickName}")
                        Log.d(TAG, "点赞数：${record.giveLikeCount}")
                        Log.d(TAG, "评论数：${record.commentCount}")
                    }
                }
            } ?: run {
                Log.w(TAG, "⚠️ 响应数据为 null")
            }
            
            Log.d(TAG, "\n========== 测试结束 ==========\n")
        } catch (e: Exception) {
            Log.e(TAG, "✗ 测试失败", e)
        }
    }
}
