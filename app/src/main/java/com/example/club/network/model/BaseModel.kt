package com.example.club.network.model

/** 统一响应基类 */
data class BaseModel<T>(
    val code: Int = 0,
    val msg: String? = null,  // 改为可空类型，适配服务端返回 null 的情况
    val data: T? = null
) {
    // 服务端返回 code=0 表示成功（而不是 200）
    val isSuccess: Boolean get() = code == 0 || code == 200
}

/** 统一分页基类 */
data class BasePageModel<T>(
    val total: Long = 0L,
    val pages: Int = 0,
    val current: Int = 1,
    val size: Int = 10,
    val records: List<T> = emptyList()
)

/** Token 信息 */
data class TokenModel(
    val accessToken: String = "",
    val refreshToken: String = "",
    val expiresIn: Long = 0L,
    val tokenType: String = "Bearer"
)

/** 用户简要信息 */
data class MineModel(
    val memberId: String = "",
    val nickName: String = "",
    val avatar: String = "",
    val phone: String = "",
    val level: Int = 0,
    val followCount: Int = 0,
    val fansCount: Int = 0,
    val likeCount: Int = 0
)

/** 完整用户信息 */
data class UserMemberInfoItem(
    val memberId: String = "",
    val nickName: String = "",
    val avatar: String = "",
    val phone: String = "",
    val openId: String = "",
    val sex: Int = 0,
    val birthday: String = "",
    val province: String = "",
    val city: String = "",
    val introduction: String = ""
)
