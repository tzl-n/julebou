package com.example.club.network.service

import com.example.club.network.api.ApiConstants
import com.example.club.network.model.BaseModel
import com.example.club.network.model.BasePageModel
import retrofit2.http.*

/**
 * 帖子模块
 * 包含：帖子列表/详情/发布/删除、点赞、收藏、分享、评论管理
 */
interface PostsApiService {

    /** 分页获取帖子列表
     * @param category 分类：0=动态 1=推荐 2=热门
     * @param topicId 话题ID（可选）
     * @param vehicleModelId 车型ID筛选（可选）
     * @param memberId 指定用户的帖子（可选）
     * @param ifMy 是否只看我的
     * @param ifFocus 是否只看关注的
     */
    @GET(ApiConstants.Posts.PAGE)
    suspend fun getPostsPage(
        @Query("category") category: Int? = null,
        @Query("topicId") topicId: String? = null,
        @Query("vehicleModelId") vehicleModelId: String? = null,
        @Query("memberId") memberId: String? = null,
        @Query("ifMy") ifMy: Boolean? = null,
        @Query("ifFocus") ifFocus: Boolean? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取帖子详情
     * @param postsId 帖子ID
     */
    @GET(ApiConstants.Posts.DETAIL)
    suspend fun getPostDetail(@Query("postsId") postsId: String): BaseModel<Any>

    /** 发布帖子
     * body 字段：content, img(图片URL数组), topicIds(话题ID数组)
     */
    @POST(ApiConstants.Posts.SAVE)
    suspend fun savePosts(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 删除帖子
     * @param postsId 帖子ID
     */
    @GET(ApiConstants.Posts.DELETE)
    suspend fun deletePosts(@Query("postsId") postsId: String): BaseModel<Boolean>

    /** 点赞/取消点赞帖子（接口自动切换状态）
     * @param postsId 帖子ID
     */
    @GET(ApiConstants.Posts.LIKE)
    suspend fun giveLike(@Query("postsId") postsId: String): BaseModel<Boolean>

    /** 分享帖子（记录分享次数）
     * @param postsId 帖子ID
     */
    @GET(ApiConstants.Posts.SHARE)
    suspend fun giveShare(@Query("postsId") postsId: String): BaseModel<Boolean>

    /** 校验帖子点赞状态（当前用户是否已点赞）
     * @param postsId 帖子ID
     */
    @GET(ApiConstants.Posts.VALIDATE_LIKE)
    suspend fun validateLike(@Query("postsId") postsId: String): BaseModel<Boolean>

    /** 分页获取帖子评论列表
     * @param postsId 帖子ID
     */
    @GET(ApiConstants.Posts.PAGE_COMMENT)
    suspend fun getCommentPage(
        @Query("postsId") postsId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 发布帖子评论
     * body 字段：postsId, content, replyCommentId(回复某条评论，可选)
     */
    @POST(ApiConstants.Posts.SAVE_COMMENT)
    suspend fun saveComment(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 删除帖子评论
     * @param commentId 评论ID
     */
    @GET(ApiConstants.Posts.DELETE_COMMENT)
    suspend fun deleteComment(@Query("commentId") commentId: String): BaseModel<Boolean>

    /** 发布评论前校验（检查是否有权限评论）
     * body 字段：postsId
     */
    @POST(ApiConstants.Posts.VALIDATE_COMMENT)
    suspend fun validateComment(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 分页获取帖子关联的售后信息
     * @param postsId 帖子ID
     */
    @GET(ApiConstants.Posts.AFTER_SALE_PAGE)
    suspend fun getAfterSalePage(
        @Query("postsId") postsId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 收藏/取消收藏内容（帖子/文章/指南/动态/路线/资讯均用此接口）
     * body 字段：collectId(内容ID), type(1=帖子 2=文章 3=指南 4=动态 5=路线 6=资讯)
     */
    @POST(ApiConstants.Posts.COLLECT)
    suspend fun giveCollect(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 批量删除收藏记录
     * body 字段：ids(收藏记录ID数组)
     */
    @POST(ApiConstants.Posts.BATCH_DELETE_COLLECT)
    suspend fun batchDeleteCollect(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 评论点赞（支持多种内容类型）
     * body 字段：commentId, type(1=帖子 2=资讯 3=文章 4=指南 5=动态 6=路线 7=团队)
     */
    @POST(ApiConstants.Posts.COMMENT_LIKE)
    suspend fun commentLike(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>
}
