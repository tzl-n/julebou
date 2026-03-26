package com.example.club.network.service

import com.example.club.network.api.ClubApiConstants
import com.example.club.network.model.BaseModel
import com.example.club.network.model.BasePageModel
import retrofit2.http.*

/**
 * 车辆管理模块
 * 包含：绑定/解绑、共享、控制指令、实时数据、轨迹、里程、排行榜、体检、安全设置、记忆、OTA升级
 */
interface VehicleApiService {

    // ========== 车辆基础操作 ==========

    /** 获取当前用户所有绑定车辆列表 */
    @GET(ClubApiConstants.Vehicle.LIST)
    suspend fun getVehicleList(): BaseModel<List<Any>>

    /** 绑定新车辆
     * body：vin(车架号), plate(车牌), modelId(车型ID), deviceSn(设备号)
     */
    @POST(ClubApiConstants.Vehicle.BIND)
    suspend fun bindVehicle(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 修改车辆信息（昵称、车牌）
     * body：vehicleId, plate, nickname
     */
    @POST(ClubApiConstants.Vehicle.UPDATE)
    suspend fun updateVehicle(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 设置默认车辆（多车时切换主车）
     * body：vehicleId
     */
    @POST(ClubApiConstants.Vehicle.SET_DEFAULT)
    suspend fun setDefaultVehicle(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 解绑车辆（需短信验证码）
     * body：vehicleId, code
     */
    @POST(ClubApiConstants.Vehicle.UNBIND)
    suspend fun unbindVehicle(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 解绑车辆上的 IoT 设备
     * body：vehicleId, deviceSn
     */
    @POST(ClubApiConstants.Vehicle.UNBIND_DEVICE)
    suspend fun unbindDevice(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 获取车辆共享用户列表
     * @param vehicleId 车辆ID
     */
    @POST(ClubApiConstants.Vehicle.SHARE_USER_LIST)
    suspend fun getShareUserList(@Query("vehicleId") vehicleId: String): BaseModel<List<Any>>

    /** 设置车辆共享开关
     * body：vehicleId, shareSwitch(true=开启)
     */
    @POST(ClubApiConstants.Vehicle.SHARE_SETUP)
    suspend fun shareSetUp(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 添加共享用户（需验证码）
     * body：vehicleId, phone, code
     */
    @POST(ClubApiConstants.Vehicle.SHARE_ADD_USER)
    suspend fun addShareUser(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 移除共享用户
     * @param vehicleId 车辆ID
     * @param memberId 被移除用户ID
     */
    @POST(ClubApiConstants.Vehicle.SHARE_DELETE_USER)
    suspend fun deleteShareUser(
        @Query("vehicleId") vehicleId: String,
        @Query("memberId") memberId: String
    ): BaseModel<Boolean>

    // ========== 车辆控制 ==========

    /** 获取车辆可用控制功能列表（寻车、上锁、解锁等）
     * @param vehicleId 车辆ID（路径参数）
     */
    @GET(ClubApiConstants.Vehicle.CONTROL_LIST)
    suspend fun getControlList(@Path("id") vehicleId: String): BaseModel<Any>

    /** 发送控制指令到车辆
     * body：vehicleId, cmd(指令如"unlock"), value
     */
    @POST(ClubApiConstants.Vehicle.CONTROL_CMD)
    suspend fun sendControlCmd(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Any>

    /** 唤醒休眠设备（发送控制指令前需先调用）
     * body：vehicleId
     */
    @POST(ClubApiConstants.Vehicle.AWAKEN)
    suspend fun awakenDevice(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 获取车辆实时状态数据（电量、速度、位置等）
     * @param vehicleId 车辆ID（路径参数）
     */
    @GET(ClubApiConstants.Vehicle.BODY_DATA)
    suspend fun getBodyData(@Path("id") vehicleId: String): BaseModel<Any>

    /** 获取历史骑行轨迹列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    @GET(ClubApiConstants.Vehicle.TRAIL)
    suspend fun getTrail(
        @Query("vehicleId") vehicleId: String,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String
    ): BaseModel<Any>

    /** 获取指定日期骑行轨迹详情
     * @param vehicleId 车辆ID（路径参数）
     * @param date 日期格式 yyyy-MM-dd（路径参数）
     */
    @GET(ClubApiConstants.Vehicle.TRAIL_DETAIL)
    suspend fun getTrailDetail(
        @Path("vehicleId") vehicleId: String,
        @Path("date") date: String
    ): BaseModel<Any>

    /** 分页获取仪表里程历史
     * @param startTime / endTime 时间范围
     */
    @GET(ClubApiConstants.Vehicle.ODO_HISTORY)
    suspend fun getOdoHistory(
        @Query("vehicleId") vehicleId: String,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取骑行统计数据
     * @param type 统计维度：day/week/month/year
     * @param vehicleId 车辆ID（路径参数）
     */
    @GET(ClubApiConstants.Vehicle.STATISTIC)
    suspend fun getStatistic(
        @Path("type") type: String,
        @Path("id") vehicleId: String
    ): BaseModel<Any>

    /** 获取骑行点亮的中国城市列表（骑行足迹地图）
     * @param vehicleId 车辆ID
     */
    @GET(ClubApiConstants.Vehicle.CHINA_LIST)
    suspend fun getChinaCityList(@Query("vehicleId") vehicleId: String): BaseModel<Any>

    /** 获取里程排行榜
     * @param rankType 排行类型（day/week/month/total）
     * @param type 范围类型
     */
    @GET(ClubApiConstants.Vehicle.RANK)
    suspend fun getRank(
        @Query("vehicleId") vehicleId: String,
        @Query("rankType") rankType: String,
        @Query("type") type: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取个人在排行榜中的位置
     * @param rankType 排行类型
     */
    @GET(ClubApiConstants.Vehicle.RANK_SELF)
    suspend fun getRankSelf(
        @Query("vehicleId") vehicleId: String,
        @Query("rankType") rankType: String,
        @Query("type") type: String
    ): BaseModel<Any>

    /** 排行榜点赞
     * body：memberId, rankType, vehicleId
     */
    @POST(ClubApiConstants.Vehicle.RANK_LIKE)
    suspend fun rankLike(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    // ========== 车辆体检 ==========

    /** 创建车辆体检记录（开始体检）
     * body：vehicleId
     */
    @POST(ClubApiConstants.Vehicle.FAULT_CREATE)
    suspend fun createFaultRecord(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 体检前连接检测（确认车辆在线可体检）
     * body：vehicleId
     */
    @POST(ClubApiConstants.Vehicle.FAULT_CHECK)
    suspend fun faultCheck(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Any>

    /** 获取上次体检数据（路径参数）
     * @param vehicleId 车辆ID
     */
    @GET(ClubApiConstants.Vehicle.FAULT_PARENT)
    suspend fun getLastFaultRecord(@Path("id") vehicleId: String): BaseModel<Any>

    /** 获取当前体检任务状态
     * @param recordId 体检记录ID
     */
    @GET(ClubApiConstants.Vehicle.FAULT_RECORD)
    suspend fun getFaultRecord(@Path("id") recordId: String): BaseModel<Any>

    /** 分页获取体检历史记录
     * @param vehicleId 车辆ID
     */
    @GET(ClubApiConstants.Vehicle.FAULT_PAGE)
    suspend fun getFaultPage(
        @Query("vehicleId") vehicleId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    // ========== 安全设置 ==========

    /** 获取车辆安全规则设置（路径参数）
     * @param vehicleId 车辆ID
     */
    @GET(ClubApiConstants.Vehicle.SECURITY_RULES)
    suspend fun getSecurityRules(@Path("vehicleId") vehicleId: String): BaseModel<Any>

    /** 更新单条安全规则
     * body：vehicleId, ruleId, status(1=开 0=关)
     */
    @POST(ClubApiConstants.Vehicle.UPDATE_RULE)
    suspend fun updateRule(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 恢复默认安全设置
     * body：vehicleId
     */
    @POST(ClubApiConstants.Vehicle.DEFAULT_RULE)
    suspend fun resetDefaultRule(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    // ========== 爱车记忆 ==========

    /** 获取爱车记忆列表
     * @param vehicleId 车辆ID（路径参数）
     */
    @GET(ClubApiConstants.Vehicle.MEMORY_LIST)
    suspend fun getMemoryList(@Path("vehicleId") vehicleId: String): BaseModel<List<Any>>

    /** 获取默认记忆详情
     * @param vehicleId 车辆ID（路径参数）
     */
    @GET(ClubApiConstants.Vehicle.MEMORY_DEFAULT)
    suspend fun getDefaultMemory(@Path("vehicleId") vehicleId: String): BaseModel<Any>

    /** 获取指定记忆详情
     * @param vehicleId 车辆ID（路径参数）
     * @param id 记忆ID（路径参数）
     */
    @GET(ClubApiConstants.Vehicle.MEMORY_BY_ID)
    suspend fun getMemoryById(
        @Path("vehicleId") vehicleId: String,
        @Path("id") id: String
    ): BaseModel<Any>

    /** 新增爱车记忆
     * body：vehicleId, name(记忆名称), params(参数对象)
     */
    @POST(ClubApiConstants.Vehicle.MEMORY_SAVE)
    suspend fun saveMemory(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 修改爱车记忆
     * body：id, name, params
     */
    @POST(ClubApiConstants.Vehicle.MEMORY_UPDATE)
    suspend fun updateMemory(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    // ========== OTA 升级 ==========

    /** 查询待升级的 OTA 任务列表
     * body：vehicleId
     */
    @POST(ClubApiConstants.Vehicle.OTA_TASKS)
    suspend fun getOtaTasks(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Any>

    /** 确认执行 OTA 升级
     * body：vehicleId, taskId
     */
    @POST(ClubApiConstants.Vehicle.OTA_CONFIRM)
    suspend fun otaConfirmUpgrade(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** ECU 确认升级
     * body：vehicleId, taskId
     */
    @POST(ClubApiConstants.Vehicle.OTA_ECU_CONFIRM)
    suspend fun ecuConfirmUpgrade(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 重新发起 OTA 升级
     * body：vehicleId, taskId
     */
    @POST(ClubApiConstants.Vehicle.OTA_REUPGRADE)
    suspend fun reupgradeOta(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 取消 ECU 升级任务
     * body：vehicleId, taskId
     */
    @POST(ClubApiConstants.Vehicle.OTA_CANCEL)
    suspend fun cancelOta(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>
}
