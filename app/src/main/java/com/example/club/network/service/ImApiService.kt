package com.example.club.network.service

import com.example.club.network.api.ClubApiConstants
import com.example.club.network.model.BaseModel
import retrofit2.http.*

/**
 * IM 群聊模块
 * 包含：创建/解散群、成员管理、全员禁言、敏感词检测
 */
interface ImApiService {

    /** 创建群聊
     * body：name(群名称), memberIds(初始成员ID数组)
     */
    @POST(ClubApiConstants.Im.CREATE_TEAM)
    suspend fun createTeam(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Any>

    /** 邀请成员入群
     * body：tid(群ID), memberIds(待邀请用户ID数组)
     */
    @POST(ClubApiConstants.Im.ADD_MEMBER)
    suspend fun addTeamMember(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 踢出群成员
     * body：tid(群ID), memberIds(待踢出用户ID数组)
     */
    @POST(ClubApiConstants.Im.KICK_MEMBER)
    suspend fun kickTeamMember(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 主动退出群聊
     * @param tid 群ID
     */
    @GET(ClubApiConstants.Im.LEAVE)
    suspend fun leaveTeam(@Query("tid") tid: String): BaseModel<Boolean>

    /** 解散群聊（仅群主可操作）
     * @param tid 群ID
     */
    @GET(ClubApiConstants.Im.REMOVE)
    suspend fun removeTeam(@Query("tid") tid: String): BaseModel<Boolean>

    /** 全员禁言/解禁
     * @param tid 群ID
     * @param ifMute 1=禁言 0=解禁
     */
    @GET(ClubApiConstants.Im.MUTE_ALL)
    suspend fun muteAll(
        @Query("tid") tid: String,
        @Query("ifMute") ifMute: Int
    ): BaseModel<Boolean>

    /** 修改群信息（如群名称）
     * body：tid, name(新群名)
     */
    @POST(ClubApiConstants.Im.UPDATE)
    suspend fun updateTeam(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 校验文字是否含敏感词
     * @param word 待检测文本
     */
    @GET(ClubApiConstants.Im.CHECK_SENSITIVE)
    suspend fun checkSensitive(@Query("word") word: String): BaseModel<Boolean>

    /** 校验是否可与某用户发起聊天
     * @param memberId 目标用户ID
     */
    @GET(ClubApiConstants.Im.CHECK_CHAT)
    suspend fun checkChat(@Query("memberId") memberId: String): BaseModel<Boolean>
}
