package com.example.club.network.interceptor

import com.example.club.BuildConfig
import com.example.club.network.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * 请求头拦截器
 * 自动为每个请求注入公共 Header：
 *   - Authorization（Bearer Token）
 *   - platform、source、version、Content-Language
 */
class HttpHeaderInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()

        // 注入 Token（非空时才加）
        if (tokenManager.isLoggedIn) {
            builder.header("Authorization", "Bearer ${tokenManager.accessToken}")
        }

        // 公共请求头
        builder
            .header("Content-Language", "zh_CN")
            .header("platform", "ANDROID")
            .header("source", "CLUB_APP")
            .header("version", BuildConfig.VERSION_NAME)

        return chain.proceed(builder.build())
    }
}
