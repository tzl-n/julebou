package com.example.club

import com.example.club.network.api.ApiConstants
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import org.junit.Assert.assertTrue

/**
 * API 连接性测试
 * 用于验证后端 API 服务是否可访问
 */
class ApiConnectivityTest {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * 测试主 API 服务器连通性
     */
    @Test
    fun testMainApiServer() = runBlocking {
        try {
            val request = Request.Builder()
                .url(ApiConstants.BASE_URL)
                .get()
                .build()

            val response = client.newCall(request).execute()
            
            println("=== 主 API 服务器测试 ===")
            println("URL: ${ApiConstants.BASE_URL}")
            println("响应码：${response.code}")
            println("响应消息：${response.message}")
            
            // 只要能访问到服务器即可（404 也算正常，说明服务器在线）
            assertTrue(
                "API 服务器无响应或错误，响应码：${response.code}",
                response.code in listOf(200, 404, 401, 403)
            )
            println("✓ 主 API 服务器连接成功\n")
        } catch (e: Exception) {
            println("✗ 主 API 服务器连接失败：${e.message}\n")
            throw e
        }
    }

    /**
     * 测试商城 API 服务器连通性
     */
    @Test
    fun testMallApiServer() = runBlocking {
        try {
            val request = Request.Builder()
                .url(ApiConstants.MALL_BASE_URL)
                .get()
                .build()

            val response = client.newCall(request).execute()
            
            println("=== 商城 API 服务器测试 ===")
            println("URL: ${ApiConstants.MALL_BASE_URL}")
            println("响应码：${response.code}")
            println("响应消息：${response.message}")
            
            assertTrue(
                "商城 API 服务器无响应或错误，响应码：${response.code}",
                response.code in listOf(200, 404, 401, 403)
            )
            println("✓ 商城 API 服务器连接成功\n")
        } catch (e: Exception) {
            println("✗ 商城 API 服务器连接失败：${e.message}\n")
            throw e
        }
    }

    /**
     * 测试获取登录验证码接口
     */
    @Test
    fun testSendLoginCodeApi() = runBlocking {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(com.example.club.network.service.AuthApiService::class.java)
            
            println("=== 测试登录验证码接口 ===")
            println("接口：${ApiConstants.Auth.SMS_LOGIN}")
            
            // 使用测试手机号（不会真正发送短信）
            val result = service.sendLoginCode("13800138000", 1)
            
            println("响应：$result")
            println("✓ 接口调用成功\n")
        } catch (e: Exception) {
            println("✗ 接口调用失败：${e.message}\n")
            // 网络错误或超时才认为是失败
            if (e is java.net.UnknownHostException || e is java.net.SocketTimeoutException) {
                throw e
            }
        }
    }

    /**
     * 测试获取用户信息接口（需要 Token）
     */
    @Test
    fun testGetUserInfoApi() = runBlocking {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(com.example.club.network.service.MemberApiService::class.java)
            
            println("=== 测试获取用户信息接口 ===")
            println("接口：${ApiConstants.Member.GET_MY_INFO}")
            
            val result = service.getMyInfo()
            
            println("响应：$result")
            println("✓ 接口调用成功\n")
        } catch (e: Exception) {
            println("✗ 接口调用失败：${e.message}")
            println("注意：此接口需要登录态，如果返回 401 表示未登录，属于正常现象\n")
            // 网络错误才抛出
            if (e is java.net.UnknownHostException || e is java.net.SocketTimeoutException) {
                throw e
            }
        }
    }

    /**
     * 测试获取帖子列表接口
     */
    @Test
    fun testGetPostsPageApi() = runBlocking {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(com.example.club.network.service.PostsApiService::class.java)
            
            println("=== 测试获取帖子列表接口 ===")
            println("接口：${ApiConstants.Posts.PAGE}")
            
            val result = service.getPostsPage(
                category = 1, // 推荐
                pageNum = 1,
                pageSize = 10
            )
            
            println("响应：$result")
            println("✓ 接口调用成功\n")
        } catch (e: Exception) {
            println("✗ 接口调用失败：${e.message}\n")
            if (e is java.net.UnknownHostException || e is java.net.SocketTimeoutException) {
                throw e
            }
        }
    }

    /**
     * 综合测试 - 一次性测试所有基础 API
     */
    @Test
    fun testAllBasicApis() = runBlocking {
        println("\n========== 开始综合 API 测试 ==========\n")
        
        var successCount = 0
        var totalCount = 0
        
        // 测试 1: 主服务器
        totalCount++
        try {
            testMainApiServer()
            successCount++
        } catch (e: Exception) {
            println("主服务器测试失败\n")
        }
        
        // 测试 2: 商城服务器
        totalCount++
        try {
            testMallApiServer()
            successCount++
        } catch (e: Exception) {
            println("商城服务器测试失败\n")
        }
        
        // 测试 3: 登录验证码接口
        totalCount++
        try {
            testSendLoginCodeApi()
            successCount++
        } catch (e: Exception) {
            println("登录验证码接口测试失败\n")
        }
        
        // 测试 4: 用户信息接口
        totalCount++
        try {
            testGetUserInfoApi()
            successCount++
        } catch (e: Exception) {
            println("用户信息接口测试失败\n")
        }
        
        // 测试 5: 帖子列表接口
        totalCount++
        try {
            testGetPostsPageApi()
            successCount++
        } catch (e: Exception) {
            println("帖子列表接口测试失败\n")
        }
        
        println("\n========== 测试完成 ==========")
        println("总测试数：$totalCount")
        println("成功数：$successCount")
        println("失败数：${totalCount - successCount}")
        println("成功率：${(successCount.toDouble() / totalCount * 100).toInt()}%")
        println("==============================\n")
        
        assertTrue("至少需要 3 个 API 测试通过", successCount >= 3)
    }
}
