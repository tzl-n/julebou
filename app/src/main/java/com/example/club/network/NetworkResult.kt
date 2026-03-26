package com.example.club.network

/**
 * 网络响应密封类，替代直接使用 try/catch
 *
 * 使用示例：
 * ```kotlin
 * when (val result = apiCall { service.getPostsPage() }) {
 *     is NetworkResult.Success -> result.data
 *     is NetworkResult.Error   -> showError(result.message)
 *     is NetworkResult.Loading -> showLoading()
 * }
 * ```
 */
sealed class NetworkResult<out T> {
    /** 请求成功，携带数据 */
    data class Success<out T>(val data: T) : NetworkResult<T>()

    /** 请求失败，携带错误信息和可选错误码 */
    data class Error(
        val message: String,
        val code: Int = -1,
        val cause: Throwable? = null
    ) : NetworkResult<Nothing>()

    /** 加载中（用于 UI 状态管理） */
    object Loading : NetworkResult<Nothing>()
}

/** 快捷判断是否成功 */
val <T> NetworkResult<T>.isSuccess get() = this is NetworkResult.Success

/** 快捷获取数据（失败时返回 null） */
fun <T> NetworkResult<T>.getOrNull(): T? =
    if (this is NetworkResult.Success) data else null

/** 成功时执行 block */
inline fun <T> NetworkResult<T>.onSuccess(block: (T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success) block(data)
    return this
}

/** 失败时执行 block */
inline fun <T> NetworkResult<T>.onError(block: (String, Int) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Error) block(message, code)
    return this
}
