package com.example.club.network.service

import com.example.club.network.api.ApiConstants
import com.example.club.network.model.BaseModel
import com.example.club.network.model.TokenModel
import retrofit2.http.*

/**
 * 用户认证模块
 * 包含：短信验证码、登录/登出、Token刷新、密码重置、微信绑定
 */
interface AuthApiService {

    /** 获取登录验证码
     * @param phone 手机号
     * @param operatorType 运营商类型
     */
    @POST(ApiConstants.Auth.SMS_LOGIN)
    suspend fun sendLoginCode(
        @Query("phone") phone: String,
        @Query("operatorType") operatorType: Int
    ): BaseModel<Boolean>

    /** 获取重置密码验证码
     * @param phone 手机号
     * @param operatorType 运营商类型
     */
    @POST(ApiConstants.Auth.SMS_FORGET_PWD)
    suspend fun sendForgetPwdCode(
        @Query("phone") phone: String,
        @Query("operatorType") operatorType: Int
    ): BaseModel<Boolean>

    /** 获取绑定手机验证码
     * @param phone 待绑定的手机号
     */
    @POST(ApiConstants.Auth.SMS_BIND_PHONE)
    suspend fun getBindingMobileCode(@Query("phone") phone: String): BaseModel<Boolean>

    /** 发送解绑车辆验证码
     * @param memberId 用户ID
     * @param vehicleId 车辆ID
     */
    @POST(ApiConstants.Auth.SMS_UNBIND_VEHICLE)
    suspend fun sendUnbindVehicleCode(
        @Query("memberId") memberId: String,
        @Query("vehicleId") vehicleId: String
    ): BaseModel<Boolean>

    /** 发送更换手机验证码
     * @param phone 新手机号
     */
    @POST(ApiConstants.Auth.SMS_UPDATE_PHONE)
    suspend fun sendUpdatePhoneCode(@Query("phone") phone: String): BaseModel<Boolean>

    /** 发送车辆共享验证码
     * @param cellphone 被共享用户手机号
     * @param vehicleId 车辆ID
     */
    @GET(ApiConstants.Auth.SMS_SHARE_VEHICLE)
    suspend fun sendVehicleShareMessage(
        @Query("cellphone") cellphone: String,
        @Query("vehicleId") vehicleId: String
    ): BaseModel<Boolean>

    /** 用户登录（密码/验证码）
     * body 字段：userName, password, grantType("password"), operatorType
     */
    @POST(ApiConstants.Auth.LOGIN)
    suspend fun login(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<TokenModel>

    /** 刷新 AccessToken（旧 Token 未过期时调用） */
    @POST(ApiConstants.Auth.REFRESH_TOKEN)
    suspend fun refreshToken(): BaseModel<TokenModel>

    /** 退出登录，服务端使当前 Token 失效 */
    @DELETE(ApiConstants.Auth.LOGOUT)
    suspend fun logout(): BaseModel<Boolean>

    /** 重置密码
     * body 字段：newPassword, smsCode, userName
     */
    @POST(ApiConstants.Auth.RESET_PWD)
    suspend fun resetPwd(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 绑定微信 openId
     * body 字段：openId, phone, code
     */
    @POST(ApiConstants.Auth.BIND_WECHAT)
    suspend fun bindWechat(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 微信验证码校验
     * body 字段：code, phone
     */
    @POST(ApiConstants.Auth.VALIDATE_CODE)
    suspend fun validateCode(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 检查手机号是否已注册
     * @param phone 手机号
     */
    @GET(ApiConstants.Auth.CHECK_PHONE_REGISTER)
    suspend fun checkPhoneRegister(@Query("phone") phone: String): BaseModel<Boolean>
}
