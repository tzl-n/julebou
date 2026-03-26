package com.example.club.network.service

import com.example.club.network.api.ApiConstants
import com.example.club.network.model.BaseModel
import com.example.club.network.model.BasePageModel
import retrofit2.http.*

/**
 * 内容模块：文章 / 指南 / 动态
 * 包含各模块的分页列表、详情、发布、删除、点赞、分享、评论管理
 */
interface ContentApiService {

    // ========== 文章模块 ==========

    /** 分页获取文章列表
     * @param category 分类
     * @param ifMy 是否只看我发布的
     * @param memberId 指定用户ID（可选）
     */
    @GET(ApiConstants.Article.PAGE)
    suspend fun getArticlePage(
        @Query("category") category: Int? = null,
        @Query("ifMy") ifMy: Boolean? = null,
        @Query("memberId") memberId: String? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取文章详情
     * @param articleId 文章ID
     */
    @GET(ApiConstants.Article.DETAIL)
    suspend fun getArticleDetail(@Query("articleId") articleId: String): BaseModel<Any>

    /** 发布文章
     * body 字段：title, content, img(图片URL数组)
     */
    @POST(ApiConstants.Article.SAVE)
    suspend fun saveArticle(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 删除文章
     * @param articleId 文章ID
     */
    @GET(ApiConstants.Article.DELETE)
    suspend fun deleteArticle(@Query("articleId") articleId: String): BaseModel<Boolean>

    /** 点赞/取消点赞文章
     * @param articleId 文章ID
     */
    @POST(ApiConstants.Article.LIKE)
    suspend fun likeArticle(@Query("articleId") articleId: String): BaseModel<Boolean>

    /** 分页获取文章评论
     * @param articleId 文章ID
     */
    @GET(ApiConstants.Article.PAGE_COMMENT)
    suspend fun getArticleCommentPage(
        @Query("articleId") articleId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 发布文章评论
     * body 字段：articleId, content, replyCommentId(可选)
     */
    @POST(ApiConstants.Article.SAVE_COMMENT)
    suspend fun saveArticleComment(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 删除文章评论
     * @param commentId 评论ID
     */
    @POST(ApiConstants.Article.DELETE_COMMENT)
    suspend fun deleteArticleComment(@Query("commentId") commentId: String): BaseModel<Boolean>

    // ========== 指南模块 ==========

    /** 获取指南标签列表（用于筛选） */
    @GET(ApiConstants.Guide.TAGS)
    suspend fun getGuideTags(): BaseModel<List<Any>>

    /** 分页获取指南列表
     * @param guideTagId 标签ID（可选）
     * @param category 分类（可选）
     * @param title 标题关键字（可选）
     */
    @GET(ApiConstants.Guide.PAGE)
    suspend fun getGuidePage(
        @Query("guideTagId") guideTagId: String? = null,
        @Query("category") category: Int? = null,
        @Query("title") title: String? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取指南详情
     * @param guideId 指南ID
     */
    @GET(ApiConstants.Guide.DETAIL)
    suspend fun getGuideDetail(@Query("guideId") guideId: String): BaseModel<Any>

    /** 点赞/取消点赞指南
     * @param guideId 指南ID
     */
    @POST(ApiConstants.Guide.LIKE)
    suspend fun likeGuide(@Query("guideId") guideId: String): BaseModel<Boolean>

    /** 分页获取指南评论
     * @param guideId 指南ID
     */
    @GET(ApiConstants.Guide.PAGE_COMMENT)
    suspend fun getGuideCommentPage(
        @Query("guideId") guideId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 发布指南评论
     * body 字段：guideId, content
     */
    @POST(ApiConstants.Guide.SAVE_COMMENT)
    suspend fun saveGuideComment(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 删除指南评论
     * @param commentId 评论ID
     */
    @POST(ApiConstants.Guide.DELETE_COMMENT)
    suspend fun deleteGuideComment(@Query("commentId") commentId: String): BaseModel<Boolean>

    // ========== 动态模块（俱乐部动态）==========

    /** 发布俱乐部动态
     * body 字段：clubId, content, img(图片URL数组), activityId(关联活动可选)
     */
    @POST(ApiConstants.Dynamic.SAVE)
    suspend fun saveDynamic(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 发布动态前校验（检查俱乐部成员权限）
     * @param clubId 俱乐部ID
     */
    @GET(ApiConstants.Dynamic.VALIDATE_SAVE)
    suspend fun validateSaveDynamic(@Query("clubId") clubId: String): BaseModel<Boolean>

    /** 分页获取动态列表
     * @param clubId 俱乐部ID（可选）
     * @param memberId 指定用户ID（可选）
     */
    @GET(ApiConstants.Dynamic.PAGE)
    suspend fun getDynamicPage(
        @Query("clubId") clubId: String? = null,
        @Query("memberId") memberId: String? = null,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 获取动态详情
     * @param dynamicId 动态ID
     */
    @GET(ApiConstants.Dynamic.DETAIL)
    suspend fun getDynamicDetail(@Query("dynamicId") dynamicId: String): BaseModel<Any>

    /** 删除动态（仅发布者或管理员可操作）
     * @param dynamicId 动态ID
     */
    @DELETE(ApiConstants.Dynamic.DELETE)
    suspend fun deleteDynamic(@Query("dynamicId") dynamicId: String): BaseModel<Boolean>

    /** 点赞/取消点赞动态
     * @param dynamicId 动态ID
     */
    @POST(ApiConstants.Dynamic.LIKE)
    suspend fun likeDynamic(@Query("dynamicId") dynamicId: String): BaseModel<Boolean>

    /** 分享动态（增加分享计数）
     * @param dynamicId 动态ID
     */
    @GET(ApiConstants.Dynamic.SHARE)
    suspend fun shareDynamic(@Query("dynamicId") dynamicId: String): BaseModel<Boolean>

    /** 发布动态评论
     * body 字段：dynamicId, content, replyCommentId(回复楼层可选)
     */
    @POST(ApiConstants.Dynamic.SAVE_COMMENT)
    suspend fun saveDynamicComment(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>

    /** 删除动态评论
     * @param commentId 评论ID
     */
    @GET(ApiConstants.Dynamic.DELETE_COMMENT)
    suspend fun deleteDynamicComment(@Query("commentId") commentId: String): BaseModel<Boolean>

    /** 分页获取动态评论列表
     * @param dynamicId 动态ID
     */
    @GET(ApiConstants.Dynamic.PAGE_COMMENT)
    suspend fun getDynamicCommentPage(
        @Query("dynamicId") dynamicId: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): BaseModel<BasePageModel<Any>>

    /** 动态评论点赞
     * body 字段：commentId, type(内容类型枚举值)
     */
    @POST(ApiConstants.Dynamic.COMMENT_LIKE)
    suspend fun dynamicCommentLike(@Body body: Map<String, @JvmSuppressWildcards Any>): BaseModel<Boolean>
}
