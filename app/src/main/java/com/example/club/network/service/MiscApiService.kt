package com.example.club.network.service

import com.example.club.network.api.ClubApiConstants
import com.example.club.network.model.BaseModel
import com.example.club.network.model.BasePageModel
import retrofit2.http.*

/**
 * 综合模块：消息通知 / 失窃上报 / 流量充值 / 意见反馈与售后 / 勋章 / 杂项辅助
 */
interface MiscApiService {

    // ========== 消息与通知 ==========

    /** 获取消息中心首页（各模块未读数汇总） */
    @GET(ClubApiConstants.Message.HOME)
    suspend fun getMessageHome(): BaseModel<Any>

    /** 分页获取消息列表
     * @param module 消息模块：1=爱车 2=系统 3=俱乐部
     */
    @GET(ClubApiConstants.Message.PAGE)
    suspend fun getMessagePage(
        @Query("module") module: Int,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取单条消息详情
     * @param messageId 消息ID
     */
    @GET(ClubApiConstants.Message.INFO)
    suspend fun getMessageInfo(@Query("messageId") messageId: String): BaseModel<Any>

    /** 删除单条消息
     * @param messageId 消息ID
     */
    @GET(ClubApiConstants.Message.DELETE)
    suspend fun deleteMessage(@Query("messageId") messageId: String): BaseModel<Boolean>

    /** 删除某模块下所有消息
     * @param module 消息模块（1/2/3）
     */
    @GET(ClubApiConstants.Message.DELETE_ALL)
    suspend fun deleteAllMessages(@Query("module") module: Int): BaseModel<Boolean>

    /** 标记某模块消息全部已读
     * @param module 消息模块（1/2/3）
     */
    @GET(ClubApiConstants.Message.READ)
    suspend fun markRead(@Query("module") module: Int): BaseModel<Boolean>

    /** 获取消息推送开关设置 */
    @GET(ClubApiConstants.Message.NOTIFY_INFO)
    suspend fun getNotifySettings(): BaseModel<Any>

    /** 更新消息推送开关
     * body：alarmType(通知类型如"car"), status(1=开 0=关)
     */
    @POST(ClubApiConstants.Message.NOTIFY_SET)
    suspend fun setNotify(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 绑定极光推送 RegistrationId
     * @param registrationId 极光分配的设备ID
     * @param platform ANDROID / IOS
     */
    @POST(ClubApiConstants.Message.JPUSH_BIND)
    suspend fun bindJPush(
        @Query("registrationId") registrationId: String,
        @Query("platform") platform: String
    ): BaseModel<Boolean>

    // ========== 失窃上报 ==========

    /** 提交车辆失窃上报
     * body：vehicleId, stolenTime, stolenLocation, contactPhone, description, img
     * @return 失窃记录ID
     */
    @POST(ClubApiConstants.Stolen.SAVE)
    suspend fun saveStolenReport(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<String>

    /** 获取失窃记录详情
     * @param id 失窃记录ID
     */
    @GET(ClubApiConstants.Stolen.INFO)
    suspend fun getStolenInfo(@Query("id") id: String): BaseModel<Any>

    /** 分页获取失窃历史记录 */
    @GET(ClubApiConstants.Stolen.PAGE)
    suspend fun getStolenPage(
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 标记车辆已找回（关闭失窃状态）
     * @param id 失窃记录ID
     */
    @GET(ClubApiConstants.Stolen.DEAL)
    suspend fun dealStolenCar(@Query("id") id: String): BaseModel<Boolean>

    // ========== 流量充值 ==========

    /** 获取流量套餐配置列表 */
    @GET(ClubApiConstants.Flow.CONFIG_LIST)
    suspend fun getFlowConfigList(): BaseModel<List<Any>>

    /** 生成流量充值订单
     * body：flowConfigId(套餐ID), vehicleId
     * @return 订单号字符串
     */
    @POST(ClubApiConstants.Flow.GENERATE_ORDER)
    suspend fun generateFlowOrder(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<String>

    /** 获取充值订单详情
     * @param orderNo 订单号
     */
    @GET(ClubApiConstants.Flow.RECHARGE_INFO)
    suspend fun getRechargeInfo(@Query("orderNo") orderNo: String): BaseModel<Any>

    /** 分页获取流量充值历史 */
    @GET(ClubApiConstants.Flow.PAGE)
    suspend fun getRechargeHistory(
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    // ========== 意见反馈与售后 ==========

    /** 提交意见反馈
     * body：feedbackType(类型), content, img(图片URL数组), phone
     */
    @POST(ClubApiConstants.Feedback.SAVE_FEEDBACK)
    suspend fun saveFeedback(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 获取客服电话列表
     * @param serviceType 服务类型（可选）
     */
    @GET(ClubApiConstants.Feedback.SERVICE_PHONE)
    suspend fun getServicePhones(
        @Query("serviceType") serviceType: String? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 提交售后申请
     * body：serviceType, content, phone, orderNo(关联订单可选)
     * @return 售后记录ID
     */
    @POST(ClubApiConstants.Feedback.SAVE_AFTER_SALE)
    suspend fun saveAfterSale(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<String>

    /** 获取售后记录详情
     * @param id 售后记录ID
     */
    @GET(ClubApiConstants.Feedback.AFTER_SALE_INFO)
    suspend fun getAfterSaleInfo(@Query("id") id: String): BaseModel<Any>

    /** 分页获取售后记录 */
    @GET(ClubApiConstants.Feedback.AFTER_SALE_PAGE)
    suspend fun getAfterSalePage(
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    // ========== 勋章系统 ==========

    /** 获取我的勋章列表 */
    @GET(ClubApiConstants.Medal.MY_LIST)
    suspend fun getMyMedals(): BaseModel<List<Any>>

    /** 获取指定用户的勋章列表
     * @param memberId 目标用户ID
     */
    @GET(ClubApiConstants.Medal.OTHER_LIST)
    suspend fun getOtherMedals(@Query("memberId") memberId: String): BaseModel<List<Any>>

    // ========== 杂项辅助（前半部分）==========

    /** 获取分享样式配置（分享图片模板） */
    @GET(ClubApiConstants.Misc.SHARE_STYLE)
    suspend fun getShareStyle(): BaseModel<Any>

    /** 获取摩圈底部导航栏配置 */
    @GET(ClubApiConstants.Misc.NAVIGATION)
    suspend fun getNavigation(): BaseModel<Any>

    /** 获取城市区域列表（省市联动）
     * @param code 上级区域代码（传0获取省份列表）
     */
    @GET(ClubApiConstants.Misc.AREA)
    suspend fun getAreaByParent(@Query("code") code: String): BaseModel<List<Any>>

    /** 获取推荐车型列表 */
    @GET(ClubApiConstants.Misc.MODELS_RECOMMEND)
    suspend fun getRecommendModels(): BaseModel<List<Any>>

    /** 获取背景视频配置 */
    @GET(ClubApiConstants.Misc.VEHICLE_VIDEO)
    suspend fun getVehicleVideo(): BaseModel<Any>

    /** 获取虚拟体验车图片配置 */
    @GET(ClubApiConstants.Misc.VIRTUAL_CAR)
    suspend fun getVirtualCar(): BaseModel<Any>

    /** 获取文件上传大小限制配置 */
    @GET(ClubApiConstants.Misc.FILE_SIZE_LIMIT)
    suspend fun getFileSizeLimit(): BaseModel<Any>

    /** 获取举报类型列表 */
    @GET(ClubApiConstants.Misc.REPORT_TYPE)
    suspend fun getReportTypes(): BaseModel<List<Any>>

    /** 屏蔽内容/用户
     * body：content(屏蔽内容), shieldType(屏蔽类型)
     */
    @POST(ClubApiConstants.Misc.SHIELD_SAVE)
    suspend fun saveShield(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 检查 App 版本更新
     * @param platform ANDROID / IOS
     */
    @GET(ClubApiConstants.Misc.VERSION_CHECK)
    suspend fun checkVersion(@Query("platform") platform: String): BaseModel<Any>

    /** 分页获取使用说明
     * @param directionsType 1=车辆说明 2=设备说明
     */
    @GET(ClubApiConstants.Misc.DIRECTIONS_USE)
    suspend fun getDirectionsUse(
        @Query("directionsType") directionsType: Int,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 提交预约试驾申请
     * body：name, phone, modelId, dealerId
     */
    @POST(ClubApiConstants.Misc.DRIVE_APPOINT_SAVE)
    suspend fun saveDriveAppointment(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 获取试驾预约详情
     * @param id 预约记录ID
     */
    @GET(ClubApiConstants.Misc.DRIVE_APPOINT_INFO)
    suspend fun getDriveAppointmentInfo(@Query("id") id: String): BaseModel<Any>

    /** 分页获取试驾预约历史 */
    @GET(ClubApiConstants.Misc.DRIVE_APPOINT_PAGE)
    suspend fun getDriveAppointmentPage(
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 分页获取我的帖子评论（评论我的/我评论的）
     * @param type 1=我评论的 2=评论我的
     */
    @GET(ClubApiConstants.Misc.MY_POSTS_COMMENT)
    suspend fun getMyPostsComment(
        @Query("type") type: Int,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 分页获取我收到的帖子点赞 */
    @GET(ClubApiConstants.Misc.MY_POSTS_LIKE_GOT)
    suspend fun getMyGotLike(
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 分页获取我收藏的帖子 */
    @GET(ClubApiConstants.Misc.MY_POSTS_COLLECT)
    suspend fun getMyPostsCollect(
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 分页获取我收藏的资讯 */
    @GET(ClubApiConstants.Misc.MY_INFO_COLLECT)
    suspend fun getMyInfoCollect(
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>
}
