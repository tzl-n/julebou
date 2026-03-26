package com.example.club.network.service

import com.example.club.network.api.ApiConstants
import com.example.club.network.model.BaseModel
import com.example.club.network.model.MineModel
import com.example.club.network.model.UserMemberInfoItem
import retrofit2.http.*

/**
 * 用户信息模块
 * 包含：获取/更新个人信息、绑定手机、修改密码、账号注销
 */
interface MemberApiService {

    /** 获取当前登录用户的简要信息（昵称、头像、等级等） */
    @GET(ApiConstants.Member.GET_MY_INFO)
    suspend fun getMyInfo(): BaseModel<MineModel>

    /** 根据用户ID获取他人简要信息
     * @param memberId 目标用户ID
     */
    @GET(ApiConstants.Member.GET_BY_MEMBER_ID)
    suspend fun getMemberById(@Query("memberId") memberId: String): BaseModel<MineModel>

    /** 获取当前用户完整个人信息（包含性别、生日、地区等） */
    @GET(ApiConstants.Member.GET_FULL_INFO)
    suspend fun getFullMemberInfo(): BaseModel<UserMemberInfoItem>

    /** 更新个人资料
     * body 字段：nickName, avatar, sex, birthday, introduction 等
     */
    @POST(ApiConstants.Member.UPDATE_MEMBER)
    suspend fun updateMember(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 绑定手机号（首次绑定）
     * body 字段：phone, code
     */
    @POST(ApiConstants.Member.BIND_PHONE)
    suspend fun bindPhone(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 更换已绑定手机号
     * body 字段：newPhone, oldPhone, code, memberId
     */
    @POST(ApiConstants.Member.UPDATE_PHONE)
    suspend fun updatePhone(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 修改登录密码
     * body 字段：oldPassword, newPassword
     */
    @POST(ApiConstants.Member.UPDATE_PWD)
    suspend fun updatePwd(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 申请注销账号
     * @param memberId 用户ID
     * @param reason 注销原因
     */
    @GET(ApiConstants.Member.APPLY_LOGOUT)
    suspend fun applyLogout(
        @Query("memberId") memberId: String,
        @Query("reason") reason: String
    ): BaseModel<Boolean>

    /** 取消注销申请
     * @param memberId 用户ID
     */
    @GET(ApiConstants.Member.CANCEL_LOGOUT)
    suspend fun cancelLogout(@Query("memberId") memberId: String): BaseModel<Boolean>

    /** 游客模式：生成临时匿名用户，用于未登录状态的基础浏览 */
    @POST(ApiConstants.Member.ADD_TOURIST)
    suspend fun addTourist(): BaseModel<Any>
}
