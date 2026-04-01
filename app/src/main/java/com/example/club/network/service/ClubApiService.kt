package com.example.club.network.service

import com.example.club.network.api.ClubApiConstants
import com.example.club.network.model.BaseModel
import com.example.club.network.model.BasePageModel
import retrofit2.http.*

/**
 * 俱乐部管理模块
 * 包含：俱乐部创建/编辑/查询、加入/退出、成员管理、评分
 */
interface ClubApiService {

    // ========== 俱乐部信息 ==========

    /** 校验当前用户是否可以创建俱乐部（每人限创建数量） */
    @GET(ClubApiConstants.Club.VERIFY_CREATE)
    suspend fun verifyCreateClub(): BaseModel<Boolean>

    /** 创建俱乐部
     * body 字段：clubName, provinceCode, cityCode, address, logo
     */
    @POST(ClubApiConstants.Club.CREATE)
    suspend fun createClub(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 编辑俱乐部信息（仅管理员可操作）
     * body 字段：clubId, clubName, logo 等
     */
    @POST(ClubApiConstants.Club.UPDATE)
    suspend fun updateClub(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 分页获取我创建的俱乐部列表 */
    @GET(ClubApiConstants.Club.MY_CREATED)
    suspend fun myCreatedClubs(
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 管理员视角获取俱乐部详情（含审核、成员统计等管理信息）
     * @param clubId 俱乐部ID
     */
    @GET(ClubApiConstants.Club.ADMIN_DETAIL)
    suspend fun getAdminClubDetail(@Query("clubId") clubId: String): BaseModel<Any>

    /** 分页获取俱乐部评分/评价列表
     * @param clubId 俱乐部ID
     */
    suspend fun getEvaluationList(
        @Query("clubId") clubId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 对俱乐部进行打分评价
     * body 字段：clubId, score(1-5), comment
     */
    @POST(ClubApiConstants.Club.SCORE)
    suspend fun scoreClub(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    // ========== 加入俱乐部 ==========

    /** 校验当前用户是否可以加入俱乐部（是否已达上限） */
    @GET(ClubApiConstants.Club.VERIFY_JOIN)
    suspend fun verifyJoinClub(): BaseModel<Boolean>

    /** 分页获取附近俱乐部列表
     * @param lat 纬度
     * @param lon 经度
     */
    @GET(ClubApiConstants.Club.NEARBY_PAGE)
    suspend fun getNearbyClubs(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取已加入俱乐部的详情（含我的成员信息）
     * @param clubId 俱乐部ID
     */
    @GET(ClubApiConstants.Club.JOINED_DETAIL)
    suspend fun getJoinedClubDetail(@Query("clubId") clubId: String): BaseModel<Any>

    /** 获取未加入俱乐部的详情（用于展示俱乐部主页）
     * @param clubId 俱乐部ID
     */
    @GET(ClubApiConstants.Club.NOT_JOIN_DETAIL)
    suspend fun getNotJoinClubDetail(@Query("clubId") clubId: String): BaseModel<Any>

    /** 申请加入俱乐部
     * body 字段：clubId, applyName, reason
     */
    @POST(ClubApiConstants.Club.APPLY_JOIN)
    suspend fun applyJoinClub(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 修改加入申请内容（审核前可修改）
     * body 字段：clubMemberId, applyName, reason
     */
    @POST(ClubApiConstants.Club.UPDATE_APPLY)
    suspend fun updateClubApply(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 取消加入申请
     * @param clubMemberId 申请记录ID
     */
    @GET(ClubApiConstants.Club.CANCEL_APPLY)
    suspend fun cancelApply(@Query("clubMemberId") clubMemberId: String): BaseModel<Boolean>

    /** 获取加入申请记录详情
     * @param clubMemberId 申请记录ID
     */
    @GET(ClubApiConstants.Club.APPLY_DETAIL)
    suspend fun getApplyDetail(@Query("clubMemberId") clubMemberId: String): BaseModel<Any>

    /** 获取我已加入/申请的所有俱乐部（含位置信息）
     * @param lat 纬度（可选，用于显示距离）
     * @param lon 经度（可选）
     */
    @GET(ClubApiConstants.Club.MY_CLUBS)
    suspend fun myClubs(
        @Query("lat") lat: Double? = null,
        @Query("lon") lon: Double? = null
    ): BaseModel<Any>

    /** 退出俱乐部
     * @param clubMemberId 成员记录ID
     * @param outWhy 退出原因（可选）
     */
    @GET(ClubApiConstants.Club.EXIT)
    suspend fun exitClub(
        @Query("clubMemberId") clubMemberId: String,
        @Query("outWhy") outWhy: String? = null
    ): BaseModel<Boolean>

    // ========== 成员管理（管理员操作）==========

    /** 分页获取俱乐部成员列表
     * @param clubId 俱乐部ID
     * @param auditState 审核状态筛选（可选）
     * @param nickName 昵称搜索（可选）
     */
    @GET(ClubApiConstants.Club.MEMBER_LIST)
    suspend fun getClubMemberList(
        @Query("clubId") clubId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("auditState") auditState: Int? = null,
        @Query("nickName") nickName: String? = null
    ): BaseModel<BasePageModel<Any>>

    /** 管理员同意成员加入申请
     * @param clubMemberId 申请记录ID
     */
    @GET(ClubApiConstants.Club.AGREE_MEMBER)
    suspend fun agreeMember(@Query("clubMemberId") clubMemberId: String): BaseModel<Boolean>

    /** 管理员拒绝成员加入申请
     * @param clubMemberId 申请记录ID
     * @param refuseReason 拒绝原因
     */
    @GET(ClubApiConstants.Club.REFUSE_MEMBER)
    suspend fun refuseMember(
        @Query("clubMemberId") clubMemberId: String,
        @Query("refuseReason") refuseReason: String
    ): BaseModel<Boolean>

    /** 管理员踢出成员
     * @param clubMemberId 成员记录ID
     * @param outWhy 踢出原因（可选）
     */
    @GET(ClubApiConstants.Club.KICK_MEMBER)
    suspend fun kickMember(
        @Query("clubMemberId") clubMemberId: String,
        @Query("outWhy") outWhy: String? = null
    ): BaseModel<Boolean>

    /** 管理员删除成员记录
     * @param clubMemberId 成员记录ID
     */
    @GET(ClubApiConstants.Club.DELETE_MEMBER)
    suspend fun deleteMember(@Query("clubMemberId") clubMemberId: String): BaseModel<Boolean>

    /** 获取成员详情（含加入时间、角色等）
     * @param clubMemberId 成员记录ID
     */
    @GET(ClubApiConstants.Club.MEMBER_DETAIL)
    suspend fun getMemberDetail(@Query("clubMemberId") clubMemberId: String): BaseModel<Any>

    /** 根据用户ID获取其在俱乐部内的成员信息
     * @param memberId 用户ID
     */
    @GET(ClubApiConstants.Club.MEMBER_BY_USER)
    suspend fun getMemberByUser(@Query("memberId") memberId: String): BaseModel<Any>
}
