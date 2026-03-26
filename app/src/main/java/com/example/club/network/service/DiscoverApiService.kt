package com.example.club.network.service

import com.example.club.network.api.ClubApiConstants
import com.example.club.network.model.BaseModel
import com.example.club.network.model.BasePageModel
import retrofit2.http.*

/**
 * 发现模块：资讯 / 团队 / 路线攻略 / 知识库
 */
interface DiscoverApiService {

    // ========== 资讯模块 ==========

    /** 获取资讯分类列表（用于顶部 Tab 切换） */
    @GET(ClubApiConstants.Information.TYPE_LIST)
    suspend fun getInfoTypes(): BaseModel<List<Any>>

    /** 分页获取资讯列表
     * @param typeId 资讯分类ID（null=全部）
     */
    @GET(ClubApiConstants.Information.PAGE)
    suspend fun getInfoPage(
        @Query("informationTypeId") typeId: String? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取资讯详情
     * @param informationId 资讯ID
     */
    @GET(ClubApiConstants.Information.DETAIL)
    suspend fun getInfoDetail(@Query("informationId") informationId: String): BaseModel<Any>

    /** 点赞/取消点赞资讯
     * @param informationId 资讯ID
     */
    @POST(ClubApiConstants.Information.LIKE)
    suspend fun likeInfo(@Query("informationId") informationId: String): BaseModel<Boolean>

    /** 分页获取资讯评论
     * @param informationId 资讯ID
     */
    @GET(ClubApiConstants.Information.PAGE_COMMENT)
    suspend fun getInfoCommentPage(
        @Query("informationId") informationId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 发表资讯评论
     * body 字段：informationId, content, parentId(回复楼层可选)
     */
    @POST(ClubApiConstants.Information.SAVE_COMMENT)
    suspend fun saveInfoComment(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 删除资讯评论
     * @param commentId 评论ID
     */
    @POST(ClubApiConstants.Information.DELETE_COMMENT)
    suspend fun deleteInfoComment(@Query("commentId") commentId: String): BaseModel<Boolean>

    // ========== 团队模块 ==========

    /** 分页获取团队列表
     * @param category 分类（可选）
     * @param ifMy 是否只看我参与的（可选）
     */
    @GET(ClubApiConstants.Team.PAGE)
    suspend fun getTeamPage(
        @Query("category") category: Int? = null,
        @Query("ifMy") ifMy: Boolean? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取团队详情
     * @param teamId 团队ID
     */
    @GET(ClubApiConstants.Team.DETAIL)
    suspend fun getTeamDetail(@Query("teamId") teamId: String): BaseModel<Any>

    /** 点赞/取消点赞团队
     * @param teamId 团队ID
     */
    @GET(ClubApiConstants.Team.LIKE)
    suspend fun likeTeam(@Query("teamId") teamId: String): BaseModel<Boolean>

    /** 分享团队（增加分享计数）
     * @param teamId 团队ID
     */
    @GET(ClubApiConstants.Team.SHARE)
    suspend fun shareTeam(@Query("teamId") teamId: String): BaseModel<Boolean>

    /** 分页获取团队评论
     * @param teamId 团队ID
     */
    @GET(ClubApiConstants.Team.PAGE_COMMENT)
    suspend fun getTeamCommentPage(
        @Query("teamId") teamId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 发表团队评论
     * body 字段：teamId, content, parentId(可选)
     */
    @POST(ClubApiConstants.Team.SAVE_COMMENT)
    suspend fun saveTeamComment(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 删除团队评论
     * @param commentId 评论ID
     */
    @POST(ClubApiConstants.Team.DELETE_COMMENT)
    suspend fun deleteTeamComment(@Query("commentId") commentId: String): BaseModel<Boolean>

    // ========== 路线攻略模块 ==========

    /** 获取路线类型列表（用于筛选 Tab） */
    @GET(ClubApiConstants.Route.TYPES)
    suspend fun getRouteTypes(): BaseModel<List<Any>>

    /** 分页查询路线攻略
     * @param provinceCode 省份代码（可选）
     * @param cityCode 城市代码（可选）
     * @param title 标题关键字（可选）
     * @param typeId 路线类型ID（可选）
     */
    @GET(ClubApiConstants.Route.PAGE)
    suspend fun getRoutePage(
        @Query("provinceCode") provinceCode: String? = null,
        @Query("cityCode") cityCode: String? = null,
        @Query("title") title: String? = null,
        @Query("routeGuideTypeId") typeId: String? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取路线详情（路径参数）
     * @param id 路线ID
     */
    @GET(ClubApiConstants.Route.DETAIL)
    suspend fun getRouteDetail(@Path("id") id: String): BaseModel<Any>

    /** 发布路线攻略
     * body 字段：title, content, img, routeGuideTypeId, provinceCode, cityCode,
     *           lat(纬度), lon(经度), useTime(用时分钟), distance(公里)
     */
    @POST(ClubApiConstants.Route.SAVE)
    suspend fun saveRoute(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 更新路线攻略
     * body 字段：同发布字段 + guideId
     */
    @POST(ClubApiConstants.Route.UPDATE)
    suspend fun updateRoute(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 删除路线攻略
     * @param guideId 路线ID
     */
    @GET(ClubApiConstants.Route.DELETE)
    suspend fun deleteRoute(@Query("guideId") guideId: String): BaseModel<Boolean>

    /** 点赞/取消点赞路线
     * @param routeGuideId 路线ID
     */
    @GET(ClubApiConstants.Route.LIKE)
    suspend fun likeRoute(@Query("routeGuideId") routeGuideId: String): BaseModel<Boolean>

    /** 分页获取路线评论
     * @param routeGuideId 路线ID
     */
    @GET(ClubApiConstants.Route.PAGE_COMMENT)
    suspend fun getRouteCommentPage(
        @Query("routeGuideId") routeGuideId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 发表路线评论
     * body 字段：routeGuideId, content, parentId(可选)
     */
    @POST(ClubApiConstants.Route.SAVE_COMMENT)
    suspend fun saveRouteComment(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 删除路线评论
     * @param commentId 评论ID
     */
    @GET(ClubApiConstants.Route.DELETE_COMMENT)
    suspend fun deleteRouteComment(@Query("commentId") commentId: String): BaseModel<Boolean>

    /** 分享路线（增加分享计数）
     * @param routeGuideId 路线ID
     */
    @GET(ClubApiConstants.Route.SHARE)
    suspend fun shareRoute(@Query("routeGuideId") routeGuideId: String): BaseModel<Boolean>

    /** 路线标题搜索建议（输入框联想）
     * @param title 搜索关键字
     */
    @GET(ClubApiConstants.Route.SEARCH_TITLE)
    suspend fun searchRouteTitle(@Query("title") title: String): BaseModel<List<Any>>

    // ========== 知识库模块 ==========

    /** 获取 FAQ 分类列表 */
    @GET(ClubApiConstants.Knowledge.FAQ_TYPES)
    suspend fun getFaqTypes(): BaseModel<List<Any>>

    /** 获取知识库分类列表 */
    @GET(ClubApiConstants.Knowledge.WIKI_TYPES)
    suspend fun getWikiTypes(): BaseModel<List<Any>>

    /** 分页获取 FAQ 列表
     * @param typeId 分类ID（可选）
     * @param name 关键字搜索（可选）
     */
    @GET(ClubApiConstants.Knowledge.FAQ_PAGE)
    suspend fun getFaqPage(
        @Query("typeId") typeId: String? = null,
        @Query("name") name: String? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 分页获取知识库文章列表
     * @param typeId 知识库分类ID（可选）
     * @param title 标题关键字（可选）
     */
    @GET(ClubApiConstants.Knowledge.WIKI_PAGE)
    suspend fun getWikiPage(
        @Query("knowledgeBaseTypeId") typeId: String? = null,
        @Query("title") title: String? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取知识库文章详情（路径参数）
     * @param id 知识库文章ID
     */
    @GET(ClubApiConstants.Knowledge.WIKI_DETAIL)
    suspend fun getWikiDetail(@Path("id") id: String): BaseModel<Any>

    /** 知识库标题关键字搜索
     * @param title 搜索关键字
     */
    @GET(ClubApiConstants.Knowledge.WIKI_SEARCH)
    suspend fun searchWiki(@Query("title") title: String): BaseModel<List<Any>>
}
