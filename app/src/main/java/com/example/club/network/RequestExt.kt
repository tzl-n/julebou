package com.example.club.network

import com.example.club.network.model.BaseModel
import com.example.club.network.model.BasePageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Flow 版网络请求封装，适合需要 collect 的场景
 *
 * 发射顺序：Loading -> Success/Error
 *
 * 示例：
 * ```kotlin
 * requestFlow { postsService.getPostsPage() }
 *     .collect { result ->
 *         when (result) {
 *             is NetworkResult.Loading -> showLoading()
 *             is NetworkResult.Success -> render(result.data)
 *             is NetworkResult.Error   -> showError(result.message)
 *         }
 *     }
 * ```
 */
fun <T> requestFlow(
    block: suspend () -> BaseModel<T>
): Flow<NetworkResult<T>> = flow {
    emit(NetworkResult.Loading)
    emit(apiCall(block))
}

/**
 * 分页数据加载封装
 *
 * 自动管理分页状态：当前页、总页数、是否还有更多
 *
 * 示例：
 * ```kotlin
 * private val paginator = Paginator(
 *     onLoad = { page -> postsService.getPostsPage(pageNum = page) },
 *     onSuccess = { data, hasMore ->
 *         _list.value = _list.value + data.records
 *         _hasMore.value = hasMore
 *     },
 *     onError = { msg -> showError(msg) }
 * )
 *
 * // 加载第一页
 * paginator.refresh()
 * // 加载下一页
 * paginator.loadMore()
 * ```
 */
class Paginator<T>(
    private val pageSize: Int = 10,
    private val onLoad: suspend (page: Int) -> BaseModel<BasePageModel<T>>,
    private val onSuccess: suspend (data: BasePageModel<T>, hasMore: Boolean) -> Unit,
    private val onError: suspend (message: String) -> Unit,
    private val onLoadingChange: ((loading: Boolean) -> Unit)? = null
) {
    private var currentPage = 1
    private var totalPages = 1
    private var isLoading = false

    /** 是否还有更多数据 */
    val hasMore: Boolean get() = currentPage <= totalPages

    /** 刷新（重置到第一页） */
    suspend fun refresh() {
        currentPage = 1
        totalPages = 1
        doLoad()
    }

    /** 加载下一页 */
    suspend fun loadMore() {
        if (isLoading || !hasMore) return
        doLoad()
    }

    private suspend fun doLoad() {
        if (isLoading) return
        isLoading = true
        onLoadingChange?.invoke(true)
        when (val result = apiCall { onLoad(currentPage) }) {
            is NetworkResult.Success -> {
                val data = result.data
                totalPages = data.pages.coerceAtLeast(1)
                val more = currentPage < totalPages
                onSuccess(data, more)
                if (more) currentPage++
            }
            is NetworkResult.Error -> onError(result.message)
            is NetworkResult.Loading -> Unit
        }
        isLoading = false
        onLoadingChange?.invoke(false)
    }

    /** 重置分页状态 */
    fun reset() {
        currentPage = 1
        totalPages = 1
        isLoading = false
    }
}
