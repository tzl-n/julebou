package com.example.club.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {

    /** 统一协程异常处理，子类可覆盖  ii*/
    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

    /**
     * 网络/业务错误回调，子类按需重写
     */
    open fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }
}
