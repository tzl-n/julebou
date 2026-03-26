package com.example.club.network.exception

/**
 * 网络异常体系
 * - [ApiException]      业务异常：服务端返回 code != 200
 * - [NetworkException]  网络层异常：无网络、超时、DNS 失败等
 * - [ParseException]    数据解析异常：JSON 格式错误
 * - [AuthException]     鉴权异常：Token 失效（code == 401）
 */

/** 业务层异常（服务端 code != 200） */
class ApiException(
    val code: Int,
    override val message: String
) : Exception(message)

/** 网络层异常（无网络/超时/连接失败） */
class NetworkException(
    override val message: String = "网络连接失败，请检查网络",
    override val cause: Throwable? = null
) : Exception(message, cause)

/** 数据解析异常 */
class ParseException(
    override val message: String = "数据解析失败",
    override val cause: Throwable? = null
) : Exception(message, cause)

/** 鉴权异常（Token 过期或未登录） */
class AuthException(
    override val message: String = "登录已过期，请重新登录"
) : Exception(message)

/** 服务器异常（5xx） */
class ServerException(
    val code: Int,
    override val message: String = "服务器异常，请稍后重试"
) : Exception(message)
