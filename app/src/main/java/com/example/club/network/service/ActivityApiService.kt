package com.example.club.network.service

import com.example.club.network.api.ClubApiConstants
import com.example.club.network.model.BaseModel
import com.example.club.network.model.BasePageModel
import retrofit2.http.*

/**
 * 俱乐部活动模块
 * 包含：活动创建/更新/终止、报名管理、订单管理、付费活动提现
 */
interface ActivityApiService {

    /** 创建俱乐部活动（仅管理员）
     * body 字段：clubId, activityName, startTime, endTime, fee(0=免费), address 等
     */
    @POST(ClubApiConstants.Activity.CREATE)
    suspend fun createActivity(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 更新活动信息（活动开始前可修改）
     * body 字段：activityId + 待修改字段
     */
    @POST(ClubApiConstants.Activity.UPDATE)
    suspend fun updateActivity(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 终止活动（提前强制结束，操作不可逆）
     * body 字段：activityId
     */
    @POST(ClubApiConstants.Activity.STOP)
    suspend fun stopActivity(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 管理员视角分页获取活动列表（含草稿/审核中/已结束等所有状态）
     * @param clubId 俱乐部ID
     * @param activityName 活动名关键字（可选）
     */
    @GET(ClubApiConstants.Activity.ADMIN_PAGE)
    suspend fun getAdminActivityPage(
        @Query("clubId") clubId: String,
        @Query("activityName") activityName: String? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 普通成员视角分页获取活动列表（仅显示已发布状态）
     * @param clubId 俱乐部ID
     * @param activityName 活动名关键字（可选）
     */
    @GET(ClubApiConstants.Activity.MEMBER_PAGE)
    suspend fun getMemberActivityPage(
        @Query("clubId") clubId: String,
        @Query("activityName") activityName: String? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取活动详情
     * @param activityId 活动ID
     */
    @GET(ClubApiConstants.Activity.DETAIL)
    suspend fun getActivityDetail(@Query("activityId") activityId: String): BaseModel<Any>

    /** 报名参加活动（付费活动需先获取未支付订单再唤起支付）
     * @param activityId 活动ID
     * @param cellphone 联系手机号
     * @param code 报名验证码
     */
    @GET(ClubApiConstants.Activity.SIGN_UP)
    suspend fun signUp(
        @Query("activityId") activityId: String,
        @Query("cellphone") cellphone: String,
        @Query("code") code: String
    ): BaseModel<Boolean>

    /** 取消报名（仅免费活动可直接取消，付费活动走退款流程）
     * @param activityId 活动ID
     */
    @GET(ClubApiConstants.Activity.CANCEL_SIGN_UP)
    suspend fun cancelSignUp(@Query("activityId") activityId: String): BaseModel<Boolean>

    /** 分页获取我报名的活动列表
     * @param clubId 俱乐部ID
     */
    @GET(ClubApiConstants.Activity.MY_ACTIVITIES)
    suspend fun getMyActivities(
        @Query("clubId") clubId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 发送报名验证码到手机
     * @param activityId 活动ID
     * @param cellphone 接收验证码的手机号
     */
    @GET(ClubApiConstants.Activity.SEND_SIGN_CODE)
    suspend fun sendSignUpCode(
        @Query("activityId") activityId: String,
        @Query("cellphone") cellphone: String
    ): BaseModel<Boolean>

    /** 分页获取活动参与人列表（管理员导出/查看）
     * @param activityId 活动ID
     */
    @GET(ClubApiConstants.Activity.PARTICIPANT_LIST)
    suspend fun getParticipantList(
        @Query("activityId") activityId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 申请提现付费活动报名费
     * @param activityId 活动ID
     * @param account 收款账户号
     * @param cellphone 手机号
     * @param code 提现验证码
     */
    @GET(ClubApiConstants.Activity.APPLY_WITHDRAW)
    suspend fun applyWithdraw(
        @Query("activityId") activityId: String,
        @Query("account") account: String,
        @Query("cellphone") cellphone: String,
        @Query("code") code: String
    ): BaseModel<Boolean>

    /** 管理员分页获取活动订单列表
     * @param clubId 俱乐部ID
     * @param orderStatus 订单状态筛选（可选）
     */
    @GET(ClubApiConstants.Activity.ADMIN_ORDER_PAGE)
    suspend fun getAdminOrderPage(
        @Query("clubId") clubId: String,
        @Query("orderStatus") orderStatus: Int? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 成员分页获取自己的活动订单列表
     * @param clubId 俱乐部ID
     * @param orderStatus 订单状态筛选（可选）
     */
    @GET(ClubApiConstants.Activity.MEMBER_ORDER_PAGE)
    suspend fun getMemberOrderPage(
        @Query("clubId") clubId: String,
        @Query("orderStatus") orderStatus: Int? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取活动订单详情
     * @param orderId 订单ID
     */
    @GET(ClubApiConstants.Activity.ORDER_DETAIL)
    suspend fun getOrderDetail(@Query("orderId") orderId: String): BaseModel<Any>

    /** 取消活动订单（未支付状态下可取消）
     * @param orderId 订单ID
     */
    @GET(ClubApiConstants.Activity.CANCEL_ORDER)
    suspend fun cancelOrder(@Query("orderId") orderId: String): BaseModel<Boolean>

    /** 申请退款（已支付订单）
     * body 字段：orderId, reason(退款原因)
     */
    @POST(ClubApiConstants.Activity.REFUND_ORDER)
    suspend fun refundOrder(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 管理员审核退款申请
     * body 字段：orderId, auditStatus(2=同意 3=拒绝)
     */
    @POST(ClubApiConstants.Activity.AUDIT_ORDER)
    suspend fun auditOrder(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>
}
