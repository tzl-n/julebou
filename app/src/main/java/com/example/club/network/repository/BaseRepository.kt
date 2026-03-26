package com.example.club.network.repository

import com.example.club.network.NetworkResult
import com.example.club.network.apiCall
import com.example.club.network.model.BaseModel

/**
 * Repository 基类
 *
 * 提供 [safeCall] 统一封装网络请求，子类直接使用：
 *
 * ```kotlin
 * @Singleton
 * class PostsRepository @Inject constructor(
 *     private val postsApiService: PostsApiService
 * ) : BaseRepository() {
 *
 *     suspend fun getPostsPage(category: Int) =
 *         safeCall { postsApiService.getPostsPage(category = category) }
 *
 *     suspend fun savePosts(content: String, imgs: List<String>) =
 *         safeCall {
 *             postsApiService.savePosts(mapOf(
 *                 "content" to content,
 *                 "img" to imgs
 *             ))
 *         }
 * }
 * ```
 *
 * ViewModel 中使用：
 * ```kotlin
 * fun loadPosts() {
 *     viewModelScope.launch {
 *         when (val result = postsRepository.getPostsPage(1)) {
 *             is NetworkResult.Success -> _posts.value = result.data
 *             is NetworkResult.Error   -> showError(result.message)
 *             else -> Unit
 *         }
 *     }
 * }
 * ```
 */
abstract class BaseRepository {

    /**
     * 统一网络请求封装
     * 自动切换 IO 线程、解析 BaseModel、映射异常
     */
    protected suspend fun <T> safeCall(
        block: suspend () -> BaseModel<T>
    ): NetworkResult<T> = apiCall(block)
}
