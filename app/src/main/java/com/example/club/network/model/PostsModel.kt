package com.example.club.network.model

import com.google.gson.annotations.SerializedName

/** 帖子信息 */
data class PostsModel(
    @SerializedName("postsId") val postsId: String = "",
    @SerializedName("memberId") val memberId: String = "",
    @SerializedName("content") val content: String = "",
    @SerializedName("img") val img: List<ImageInfo>? = null,
    @SerializedName("topicIds") val topicIds: List<String>? = null,
    @SerializedName("giveLikeCount") var giveLikeCount: Int = 0,
    @SerializedName("commentCount") val commentCount: Int = 0,
    @SerializedName("shareCount") val shareCount: Int = 0,
    @SerializedName("collectCount") val collectCount: Int = 0,
    @SerializedName("createTime") val createTime: String = "",
    @SerializedName("nickName") val nickName: String = "",
    @SerializedName("userName") val userName: String = "",
    @SerializedName("avatar") val avatar: AvatarInfo? = null,
    @SerializedName("vehicleModelName") val vehicleModelName: String = "",
    @SerializedName("isGiveLike") var isGiveLike: Boolean = false,
    @SerializedName("isCollect") val isCollect: Boolean = false,
    // 其他字段（根据 API 返回补充）
    @SerializedName("series") val series: String = "",
    @SerializedName("vehicleModelId") val vehicleModelId: String = "",
    @SerializedName("modelNickname") val modelNickname: String = "",
    @SerializedName("modelName") val modelName: String = "",
    @SerializedName("channel") val channel: Int = 0,
    @SerializedName("channelStr") val channelStr: String = "",
    @SerializedName("virtualShareNumber") val virtualShareNumber: Int = 0,
    @SerializedName("virtualLikeNumber") val virtualLikeNumber: Int = 0,
    @SerializedName("shareNumber") val shareNumber: Int = 0,
    @SerializedName("likeNumber") val likeNumber: Int = 0,
    @SerializedName("commentNumber") val commentNumber: Int = 0,
    @SerializedName("collectNumber") val collectNumber: Int = 0,
    @SerializedName("topics") val topics: List<TopicInfo>? = null,
    @SerializedName("topicStr") val topicStr: String = ""
)

/** 图片信息 */
data class ImageInfo(
    @SerializedName("url") val url: String = "",
    @SerializedName("filename") val filename: String = "",
    @SerializedName("thumbnailUrl") val thumbnailUrl: String = "",
    @SerializedName("contentLength") val contentLength: Long = 0L,
    @SerializedName("contentType") val contentType: String = "",
    @SerializedName("imageHeight") val imageHeight: Int = 0,
    @SerializedName("imageWidth") val imageWidth: Int = 0
)

/** 头像信息 */
data class AvatarInfo(
    @SerializedName("url") val url: String = "",
    @SerializedName("filename") val filename: String = "",
    @SerializedName("thumbnailUrl") val thumbnailUrl: String = "",
    @SerializedName("contentLength") val contentLength: Long = 0L,
    @SerializedName("contentType") val contentType: String = "",
    @SerializedName("imageHeight") val imageHeight: Int = 0,
    @SerializedName("imageWidth") val imageWidth: Int = 0
)

/** 话题信息 */
data class TopicInfo(
    @SerializedName("topicId") val topicId: String = "",
    @SerializedName("topicName") val topicName: String = ""
)
