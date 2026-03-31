package com.example.club

import com.example.club.utils.ApiTestUtil
import org.junit.Test

/**
 * 简单的 API 测试示例
 */
class SimpleApiTest {

    @Test
    fun testServers() {
        println("\n=== 开始快速测试 ===\n")
        
        // 直接调用 ApiConnectivityTest 中的测试方法
        val test = ApiConnectivityTest()
        try {
            test.testMainApiServer()
            println("✓ 主 API 服务器连接成功")
        } catch (e: Exception) {
            println("✗ 主 API 服务器连接失败：${e.message}")
        }
        
        try {
            test.testMallApiServer()
            println("✓ 商城 API 服务器连接成功")
        } catch (e: Exception) {
            println("✗ 商城 API 服务器连接失败：${e.message}")
        }
    }
}
