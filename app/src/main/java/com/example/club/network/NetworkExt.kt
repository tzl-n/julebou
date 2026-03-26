package com.example.club.network

import com.example.club.network.exception.*
import com.example.club.network.model.BaseModel
import com.google.gson.JsonParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 统一网络请求封装
 *
 * 自动完成：
 * 1. 切换到 IO 线程
 * 2. 解析 BaseModel，检查业务 code
 * 3. 将所有异常映射为语义化的 [NetworkResult.Error]
 *
 * 使用示例：
 * ```kotlin
 * val result = apiCall { postsApiService.getPostsPage() }
 * result.onSuccess { page -> ... }
 *       .onError  { msg, code -> ... }
 * ```
 */
suspend fun <T> apiCall(
    block: suspend () -> BaseModel<T>
): NetworkResult<T> = withContext(Dispatchers.IO) {
    try {
        val response = block()
        if (response.isSuccess) {
            val data = response.data
            if (data != null) {
                NetworkResult.Success(data)
            } else {
                // data 为 null 时视业务场景而定，这里返回成功但 data 为空
                @Suppress("UNCHECKED_CAST")
                NetworkResult.Success(Unit as T)
            }
        } else {
            when (response.code) {
                401 -> NetworkResult.Error(
                    message = "登录已过期，请重新登录",
                    code = 401,
                    cause = AuthException()
                )
                else -> NetworkResult.Error(
                    message = response.msg.ifBlank { "请求失败(${response.code})" },
                    code = response.code
                )
            }
        }
    } catch (e: Exception) {
        NetworkResult.Error(
            message = e.toReadableMessage(),
            code = e.toErrorCode(),
            cause = e
        )
    }
}

/**
 * 将异常转换为用户可读的错误提示
 */
fun Throwable.toReadableMessage(): String = when (this) {
    is AuthException         -> message ?: "登录已过期，请重新登录"
    is ApiException          -> message
    is SocketTimeoutException -> "请求超时，请稍后重试"
    is UnknownHostException  -> "无法连接服务器，请检查网络"
    is ConnectException      -> "网络连接失败，请检查网络"
    is HttpException         -> when (code()) {
        401 -> "登录已过期，请重新登录"
        403 -> "无访问权限"
        404 -> "请求的资源不存在"
        in 500..599 -> "服务器异常，请稍后重试"
        else -> "网络异常(${code()})"
    }
    is JsonParseException    -> "数据解析失败"
    is NetworkException      -> message ?: "网络连接失败"
    else                     -> "未知错误：${message ?: "请稍后重试"}"
}

/**
 * 将异常映射为错误码
 */
fun Throwable.toErrorCode(): Int = when (this) {
    is AuthException          -> 401
    is ApiException           -> code
    is HttpException          -> code()
    is SocketTimeoutException -> -1001
    is UnknownHostException   -> -1002
    is ConnectException       -> -1003
    is JsonParseException     -> -2001
    else                      -> -1
}
