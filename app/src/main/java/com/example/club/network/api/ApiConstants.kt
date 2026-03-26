package com.example.club.network.api

object ApiConstants {
    const val BASE_URL = "https://iovapicyclone.zonsenmotor.com/"
    const val MALL_BASE_URL = "http://test-zsjc-openapi.qimoyun.com/"

    object Auth {
        const val SMS_LOGIN = "club-system/sms/sendLoginCode"
        const val SMS_FORGET_PWD = "club-system/sms/sendForgetPwdCode"
        const val SMS_BIND_PHONE = "club-system/sms/getBindingMobileCode"
        const val SMS_UNBIND_VEHICLE = "club-system/sms/sendUnbindVehicleCode"
        const val SMS_UPDATE_PHONE = "club-system/sms/sendUpdatePhoneCode"
        const val SMS_SHARE_VEHICLE = "moto/app/vehicle/share/sendVehicleShareMessage"
        const val LOGIN = "club-auth/login"
        const val REFRESH_TOKEN = "club-auth/refresh"
        const val LOGOUT = "club-auth/logout"
        const val RESET_PWD = "club-auth/resetPwd"
        const val BIND_WECHAT = "club-system/app/member/bindingOpenId"
        const val VALIDATE_CODE = "club-system/app/member/validationCode"
        const val CHECK_PHONE_REGISTER = "club-system/app/member/checkPhoneRegister"
    }

    object Member {
        const val GET_MY_INFO = "club-moto/app/member/getInfo"
        const val GET_BY_MEMBER_ID = "club-moto/app/member/getByMemberIdInfo"
        const val GET_FULL_INFO = "club-system/app/member/getByMemberInfo"
        const val UPDATE_MEMBER = "club-system/app/member/updateMember"
        const val BIND_PHONE = "club-system/app/member/bindingPhone"
        const val UPDATE_PHONE = "club-system/app/member/updateMemberPhone"
        const val UPDATE_PWD = "club-system/app/member/updatePwd"
        const val APPLY_LOGOUT = "club-system/app/memberLogout/saveMemberLogout"
        const val CANCEL_LOGOUT = "club-system/app/memberLogout/cancel"
        const val ADD_TOURIST = "club-system/app/member/addMemberTourists"
    }

    object Posts {
        const val PAGE = "club-moto/app/postsInfo"
        const val DETAIL = "club-moto/app/postsInfo/getByPostsId"
        const val SAVE = "club-moto/app/postsInfo/savePosts"
        const val DELETE = "club-moto/app/postsInfo/deletePosts"
        const val LIKE = "club-moto/app/postsInfo/giveLike"
        const val SHARE = "club-moto/app/postsInfo/giveShare"
        const val VALIDATE_LIKE = "club-moto/app/postsInfo/giveValidationLike"
        const val PAGE_COMMENT = "club-moto/app/postsInfo/pageComment"
        const val SAVE_COMMENT = "club-moto/app/postsInfo/saveComment"
        const val DELETE_COMMENT = "club-moto/app/postsInfo/deleteComment"
        const val VALIDATE_COMMENT = "club-moto/app/postsInfo/saveValidationComment"
        const val AFTER_SALE_PAGE = "club-moto/app/postsInfo/palePage"
        const val COLLECT = "club-moto/app/collect/giveCollect"
        const val BATCH_DELETE_COLLECT = "club-moto/app/collect/batchDelete"
        const val COMMENT_LIKE = "club-moto/app/commentLike/giveLike"
    }

    object Article {
        const val PAGE = "club-moto/app/articleInfo"
        const val DETAIL = "club-moto/app/articleInfo/getByArticleId"
        const val SAVE = "club-moto/app/articleInfo/saveArticle"
        const val DELETE = "club-moto/app/articleInfo/deleteRoute"
        const val LIKE = "club-moto/app/articleInfo/giveLike"
        const val SHARE = "club-moto/app/articleInfo/giveShare"
        const val VALIDATE_LIKE = "club-moto/app/articleInfo/giveValidationLike"
        const val PAGE_COMMENT = "club-moto/app/articleInfo/pageComment"
        const val SAVE_COMMENT = "club-moto/app/articleInfo/saveComment"
        const val DELETE_COMMENT = "club-moto/app/articleInfo/deleteComment"
        const val AFTER_SALE_PAGE = "club-moto/app/articleInfo/palePage"
    }

    object Guide {
        const val TAGS = "club-moto/app/guideInfo/findGuideTag"
        const val PAGE = "club-moto/app/guideInfo"
        const val DETAIL = "club-moto/app/guideInfo/getByGuideId"
        const val LIKE = "club-moto/app/guideInfo/giveLike"
        const val SHARE = "club-moto/app/guideInfo/giveShare"
        const val VALIDATE_LIKE = "club-moto/app/guideInfo/giveValidationLike"
        const val PAGE_COMMENT = "club-moto/app/guideInfo/pageComment"
        const val SAVE_COMMENT = "club-moto/app/guideInfo/saveComment"
        const val DELETE_COMMENT = "club-moto/app/guideInfo/deleteComment"
    }

    object Dynamic {
        const val SAVE = "club/app/dynamicInfo/saveDynamic"
        const val VALIDATE_SAVE = "club/app/dynamicInfo/saveDynamicValidation"
        const val PAGE = "club/app/dynamicInfo"
        const val DETAIL = "club/app/dynamicInfo/getByDynamicId"
        const val DELETE = "club/app/dynamicInfo/deleteDynamicInfo"
        const val LIKE = "club/app/dynamicInfo/giveLike"
        const val SHARE = "club/app/dynamicInfo/giveShare"
        const val SAVE_COMMENT = "club/app/dynamicInfo/saveComment"
        const val DELETE_COMMENT = "club/app/dynamicInfo/deleteComment"
        const val PAGE_COMMENT = "club/app/dynamicInfo/pageComment"
        const val COMMENT_LIKE = "club-moto/app/clubCommentLike/giveLike"
        const val MY_GOT_LIKE = "club/app/clubMyGiveALike/myGotDynamicLike"
        const val MY_COMMENT = "club/app/clubMyGiveALike/myPageDynamicComment"
        const val MY_CLUB_INFO = "club/app/clubMyGiveALike/myClubInfo"
    }
}
