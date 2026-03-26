package com.example.club.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.club.network.NetworkResult
import com.example.club.network.apiCall
import com.example.club.network.model.BaseModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * MVVM 基础 ViewModel
 *
 * 提供：
 * - [uiState]       UI 状态流（Loading / Success / Error）
 * - [toastEvent]    全局 Toast 事件流（单次消费）
 * - [launch]        协程启动 + 自动异常捕获
 * - [request]       一行代码完成网络请求 + 状态管理
 */
open class BaseViewModel : ViewModel() {

    // ---- UI 状态 ----

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    /** UI 加载状态（Idle / Loading / Success / Error） */
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    /** 全局 Toast 事件（用 SharedFlow 保证单次消费） */
    val toastEvent: SharedFlow<String> = _toastEvent.asSharedFlow()

    // ---- 状态控制 ----

    protected fun showLoading() { _uiState.value = UiState.Loading }
    protected fun showSuccess() { _uiState.value = UiState.Success }
    protected fun showIdle()    { _uiState.value = UiState.Idle }
    protected fun showError(msg: String) {
        _uiState.value = UiState.Error(msg)
        toast(msg)
    }

    protected fun toast(msg: String) {
        viewModelScope.launch { _toastEvent.emit(msg) }
    }

    // ---- 协程启动 ----

    /**
     * 带全局异常捕获的协程启动
     * @param showLoading 是否自动显示/隐藏 Loading 状态
     * @param onError 自定义错误处理（不传则弹 Toast）
     */
    protected fun launch(
        showLoading: Boolean = false,
        onError: ((String) -> Unit)? = null,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (showLoading) showLoading()
                block()
                if (showLoading) showSuccess()
            } catch (e: Exception) {
                val msg = e.message ?: "未知错误"
                if (onError != null) onError(msg)
                else showError(msg)
            }
        }
    }

    /**
     * 一行代码完成网络请求 + 状态管理
     *
     * 用法：
     * ```kotlin
     * request(
     *     showLoading = true,
     *     call = { postsApiService.getPostsPage() },
     *     onSuccess = { page -> _posts.value = page }
     * )
     * ```
     */
    protected fun <T> request(
        showLoading: Boolean = true,
        call: suspend () -> BaseModel<T>,
        onSuccess: (T) -> Unit,
        onError: ((String) -> Unit)? = null
    ) {
        viewModelScope.launch {
            if (showLoading) showLoading()
            when (val result = apiCall(call)) {
                is NetworkResult.Success -> {
                    showSuccess()
                    onSuccess(result.data)
                }
                is NetworkResult.Error -> {
                    val msg = result.message
                    if (onError != null) {
                        showIdle()
                        onError(msg)
                    } else {
                        showError(msg)
                    }
                }
                is NetworkResult.Loading -> { /* 不会走到这里 */ }
            }
        }
    }

    /** 网络错误/业务错误回调，子类按需重写 */
    open fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }
}

/**
 * UI 状态密封类
 */
sealed class UiState {
    /** 初始/空闲状态 */
    object Idle : UiState()
    /** 加载中 */
    object Loading : UiState()
    /** 加载成功 */
    object Success : UiState()
    /** 加载失败 */
    data class Error(val message: String) : UiState()
}
