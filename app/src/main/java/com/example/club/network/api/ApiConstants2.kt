package com.example.club.network.api

object ClubApiConstants {
    object Club {
        const val VERIFY_CREATE = "club/app/club/verifyCreateClub"
        const val CREATE = "club/app/club/appCreateClubInfo"
        const val UPDATE = "club/app/club/appUpdateClubInfo"
        const val MY_CREATED = "club/app/club/myCreatedClub"
        const val ADMIN_DETAIL = "club/app/club/getByAdminClubDetails"
        const val EVALUATION_LIST = "club/app/club/appClubEvaluation"
        const val SCORE = "club/app/clubMember/upDownClub"
        const val VERIFY_JOIN = "club/app/clubMember/verifyJoinClub"
        const val NEARBY_PAGE = "club/app/clubMember/appPage"
        const val JOINED_DETAIL = "club/app/clubMember/getByClubId"
        const val NOT_JOIN_DETAIL = "club/app/clubMember/getByNotJoinClubId"
        const val APPLY_JOIN = "club/app/clubMember/saveClubMemberAudit"
        const val UPDATE_APPLY = "club/app/clubMember/updateClubMemberAudit"
        const val CANCEL_APPLY = "club/app/clubMember/cancelApplyClubMember"
        const val APPLY_DETAIL = "club/app/clubMember/getByAuditClubMemberId"
        const val MY_CLUBS = "club/app/clubMember/myClub"
        const val EXIT = "club/app/clubMember/appUpDownClub"
        const val EXIT_VALIDATE = "club/app/clubMember/validationUpDownClub"
        const val MEMBER_LIST = "club/app/club/pageAppClubMember"
        const val AGREE_MEMBER = "club/app/club/appAdminAgreeClubMember"
        const val REFUSE_MEMBER = "club/app/club/appAdminRefusedClubMember"
        const val KICK_MEMBER = "club/app/club/appAdminUpDownClub"
        const val DELETE_MEMBER = "club/app/club/appDeleteClubMember"
        const val MEMBER_DETAIL = "club/app/clubMember/getByClubMemberId"
        const val MEMBER_BY_USER = "club/app/clubMember/getByMemberId"
    }

    object Activity {
        const val CREATE = "club/app/activity/saveActivity"
        const val UPDATE = "club/app/activity/updateActivity"
        const val STOP = "club/app/activity/stopActivity"
        const val ADMIN_PAGE = "club/app/activity/pageManager"
        const val MEMBER_PAGE = "club/app/activity"
        const val DETAIL = "club/app/activity/getByActivityId"
        const val SIGN_UP = "club/app/activityMember/signUp"
        const val CANCEL_SIGN_UP = "club/app/activityMember/cancelSignUp"
        const val MY_ACTIVITIES = "club/app/activityMember"
        const val SEND_SIGN_CODE = "club/app/activityMember/sendSignUpMessage"
        const val PARTICIPANT_LIST = "club/app/activity/pageMember"
        const val APPLY_WITHDRAW = "club/app/activity/applyWithdraw"
        const val ADMIN_ORDER_PAGE = "club/app/activity/pageOrder"
        const val MEMBER_ORDER_PAGE = "club/app/activityMember/pageOrder"
        const val ORDER_DETAIL = "club/app/activity/getByOrderId"
        const val CANCEL_ORDER = "club/app/activityMember/cancelOrder"
        const val REFUND_ORDER = "club/app/activityMember/refundOrder"
        const val AUDIT_ORDER = "club/app/activity/auditOrder"
    }

    object Information {
        const val TYPE_LIST = "club-moto/app/information/getByList"
        const val PAGE = "club-moto/app/information"
        const val DETAIL = "club-moto/app/information/getByArticleId"
        const val LIKE = "club-moto/app/information/giveLike"
        const val PAGE_COMMENT = "club-moto/app/information/pageComment"
        const val SAVE_COMMENT = "club-moto/app/information/saveComment"
        const val DELETE_COMMENT = "club-moto/app/information/deleteComment"
        const val MY_COMMENT = "club-moto/app/my/myPageInformationComment"
        const val MY_GOT_LIKE = "club-moto/app/my/myGotInformationLike"
    }

    object Team {
        const val PAGE = "club-moto/app/teamInfo"
        const val DETAIL = "club-moto/app/teamInfo/getByTeamId"
        const val LIKE = "club-moto/app/teamInfo/giveLike"
        const val SHARE = "club-moto/app/teamInfo/giveShare"
        const val PAGE_COMMENT = "club-moto/app/teamInfo/pageComment"
        const val SAVE_COMMENT = "club-moto/app/teamInfo/saveComment"
        const val DELETE_COMMENT = "club-moto/app/teamInfo/deleteComment"
        const val MY_COMMENT = "club-moto/app/my/myPageTeamComment"
        const val MY_GOT_LIKE = "club-moto/app/my/myGotTeamLike"
    }

    object Route {
        const val TYPES = "club-moto/app/routeGuide/types"
        const val PAGE = "club-moto/app/routeGuide"
        const val DETAIL = "club-moto/app/routeGuide/details/{id}"
        const val SAVE = "club-moto/app/routeGuide/saveRouteGuide"
        const val UPDATE = "club-moto/app/routeGuide/updateRouteGuide"
        const val DELETE = "club-moto/app/routeGuide/deleteClubRouteGuide"
        const val LIKE = "club-moto/app/routeGuide/comment/giveLike"
        const val PAGE_COMMENT = "club-moto/app/routeGuide/comment/pageComment"
        const val SAVE_COMMENT = "club-moto/app/routeGuide/comment/saveComment"
        const val DELETE_COMMENT = "club-moto/app/routeGuide/comment/deleteComment"
        const val SHARE = "club-moto/app/routeGuide/comment/giveShare"
        const val SEARCH_TITLE = "club-moto/app/routeGuide/searchTitle"
        const val MY_COLLECTION = "club-moto/app/my/myRouteGuideCollection"
        const val MY_LIKE = "club-moto/app/my/myPageRouteGuideLike"
        const val MY_COMMENT = "club-moto/app/my/myPageRouteComment"
    }

    object Knowledge {
        const val FAQ_TYPES = "club-system/app/checkingManualType/all"
        const val WIKI_TYPES = "club-moto/app/knowledgeBase/allTypes"
        const val FAQ_PAGE = "club-system/app/checkingManual"
        const val WIKI_PAGE = "club-moto/app/knowledgeBase"
        const val WIKI_DETAIL = "club-moto/app/knowledgeBase/details/{id}"
        const val WIKI_SEARCH = "club-moto/app/knowledgeBase/searchTitle"
    }

    object Stolen {
        const val SAVE = "club-moto/app/loseApply/save"
        const val INFO = "club-moto/app/loseApply/info"
        const val PAGE = "club-moto/app/loseApply"
        const val DEAL = "club-moto/app/loseApply/deal"
    }

    object Flow {
        const val CONFIG_LIST = "club-moto/app/topUpRecord/configList"
        const val GENERATE_ORDER = "club-moto/app/topUpRecord/generateRecordOrder"
        const val RECHARGE_INFO = "club-moto/app/topUpRecord/getInfo"
        const val PAGE = "club-moto/app/topUpRecord"
    }

    object Feedback {
        const val SAVE_FEEDBACK = "club-moto/app/opinion/save"
        const val SERVICE_PHONE = "club-system/servicePhone"
        const val SAVE_AFTER_SALE = "club-moto/app/concatServer/save"
        const val AFTER_SALE_INFO = "club-moto/app/concatServer/info"
        const val AFTER_SALE_PAGE = "club-moto/app/concatServer"
    }

    object Medal {
        const val MY_LIST = "moto/app/medalCollection/getMyListMedalCollectionRecord"
        const val OTHER_LIST = "club-moto/app/member/getByMemberMedalInfo"
    }

    object Vehicle {
        const val LIST = "moto/app/vehicle"
        const val BIND = "moto/app/vehicle/bind"
        const val UPDATE = "moto/app/vehicle/update"
        const val SET_DEFAULT = "moto/app/vehicle/updateDefault"
        const val UNBIND = "moto/app/vehicle/unbind"
        const val UNBIND_DEVICE = "moto/app/vehicle/unbindDevice"
        const val SHARE_USER_LIST = "moto/app/vehicle/share/findShareUserList"
        const val SHARE_SETUP = "moto/app/vehicle/share/shareSetUp"
        const val SHARE_ADD_USER = "moto/app/vehicle/share/addUser"
        const val SHARE_DELETE_USER = "moto/app/vehicle/share/deleteUser"
        const val CONTROL_LIST = "moto/app/vehicle/contorl/{id}"
        const val CONTROL_CMD = "moto/app/vehicle/contorl/command"
        const val AWAKEN = "moto/app/vehicle/contorl/awakenDevice"
        const val BODY_DATA = "moto/app/vehicle/body/{id}"
        const val TRAIL = "moto/app/vehicle/trail"
        const val TRAIL_DETAIL = "moto/app/vehicle/{vehicleId}/detail/{date}"
        const val ODO_HISTORY = "moto/app/vehicle/odoHistory"
        const val STATISTIC = "club-moto/app/statistic/{type}/{id}"
        const val CHINA_LIST = "club-moto/app/statistic/chinaList"
        const val RANK = "club-moto/app/rank"
        const val RANK_SELF = "club-moto/app/rank/self"
        const val RANK_LIKE = "club-moto/app/rank/updateLike"
        const val RANK_SHARE = "club-moto/app/rank/share"
        const val FAULT_CREATE = "moto/app/vehicle/fault/createRecord"
        const val FAULT_CHECK = "moto/app/vehicle/fault/check"
        const val FAULT_PARENT = "moto/app/vehicle/fault/parentRecord/{id}"
        const val FAULT_RECORD = "moto/app/vehicle/fault/record/{id}"
        const val FAULT_PAGE = "moto/app/vehicle/fault"
        const val SECURITY_RULES = "club-moto/app/rule/{vehicleId}"
        const val UPDATE_RULE = "club-moto/app/rule/update"
        const val DEFAULT_RULE = "club-moto/app/rule/updateDefault"
        const val MEMORY_LIST = "moto/app/vehicle/memory/list/{vehicleId}"
        const val MEMORY_DEFAULT = "moto/app/vehicle/memory/{vehicleId}"
        const val MEMORY_BY_ID = "moto/app/vehicle/memory/{vehicleId}/{id}"
        const val MEMORY_SAVE = "moto/app/vehicle/memory/saveMemory"
        const val MEMORY_UPDATE = "moto/app/vehicle/memory/updateMemory"
        const val THRESHOLD = "club-moto/console/device/threshold/vehicleThresholdSet"
        const val OTA_TASKS = "moto/app/vehicle/contorl/getTaskstoUpgraded"
        const val OTA_JOB = "moto/app/vehicle/contorl/listOTATaskByJob"
        const val OTA_CONFIRM = "moto/app/vehicle/contorl/otaConfirmUpgrade"
        const val OTA_ECU_CONFIRM = "moto/app/vehicle/contorl/ecuConfirmUpgrade"
        const val OTA_REUPGRADE = "moto/app/vehicle/contorl/reupgradeOTATask"
        const val OTA_CANCEL = "moto/app/vehicle/contorl/cancelUpgrade"
    }

    object Message {
        const val HOME = "club-system/app/message/"
        const val PAGE = "club-system/app/message/page"
        const val INFO = "club-system/app/message/info"
        const val DELETE = "club-system/app/message/delete"
        const val DELETE_ALL = "club-system/app/message/deleteAll"
        const val READ = "club-system/app/message/upReadStatus"
        const val NOTIFY_INFO = "club-system/app/memberNotify/info"
        const val NOTIFY_SET = "club-system/app/memberNotify/set"
        const val JPUSH_BIND = "club-system/app/jPushReg/bindRid"
    }

    object Misc {
        const val UPLOAD_FILE = "club-system/file/uploadBatch"
        const val VERSION_CHECK = "club-system/appLevel/check"
        const val SHARE_STYLE = "club-system/shareStyle"
        const val NAVIGATION = "club-moto/app/navigation"
        const val AREA = "club-system/area/getAreaByParent"
        const val MODELS_RECOMMEND = "club-system/app/modelsRecommend/list"
        const val VEHICLE_VIDEO = "club-system/app/dict/data/getVehicleVideo"
        const val VIRTUAL_CAR = "club-system/app/dict/data/getVirtualExperienceCar"
        const val FILE_SIZE_LIMIT = "club-system/app/dict/data/getIvSizeLimit"
        const val REPORT_TYPE = "club-moto/app/tipOff/list"
        const val SHIELD_SAVE = "club-moto/app/shield/save"
        const val BATTERY_INFO = "club-moto/app/batteryRec/info"
        const val BATTERY_CONSUME = "club-moto/app/batteryRec/consumePower"
        const val APP_VERSION_PAGE = "club-moto/app/appVersion"
        const val APP_VERSION_INFO = "club-moto/app/appVersion/info"
        const val DIRECTIONS_USE = "club-system/app/directionsUse"
        const val DRIVE_APPOINT_SAVE = "club-moto/app/driveAppointment/save"
        const val DRIVE_APPOINT_INFO = "club-moto/app/driveAppointment/info"
        const val DRIVE_APPOINT_PAGE = "club-moto/app/driveAppointment"
        const val AFTER_SALE_COUNT = "club-moto/app/motorcycle/circle/paleCount"
        const val AFTER_SALE_READ = "club-moto/app/motorcycle/circle/readTime"
        const val MY_POSTS_COMMENT_COUNT = "club-moto/app/my/myPostsCommentNumber"
        const val MY_POSTS_LIKE_COUNT = "club-moto/app/my/myPostsLikeCommentNumber"
        const val MY_POSTS_COMMENT = "club-moto/app/my/myPagePostsComment"
        const val MY_POSTS_LIKE_GOT = "club-moto/app/my/myGotPostsLike"
        const val MY_POSTS_LIKE = "club-moto/app/my/myPagePostsLike"
        const val MY_POSTS_COLLECT = "club-moto/app/my/myPostsCollection"
        const val MY_INFO_COLLECT = "club-moto/app/my/myInformationCollection"
        const val MY_INFO_LIKE = "club-moto/app/my/myPageInformationLike"
    }

    object Im {
        const val CREATE_TEAM = "club-im/app/team/create"
        const val ADD_MEMBER = "club-im/app/team/add"
        const val KICK_MEMBER = "club-im/app/team/kick"
        const val LEAVE = "club-im/app/team/leave"
        const val REMOVE = "club-im/app/team/remove"
        const val MUTE_ALL = "club-im/app/team/muteTlistAll"
        const val UPDATE = "club-im/app/team/update"
        const val CHECK_SENSITIVE = "club-system/app/sensitive/check"
        const val CHECK_CHAT = "club-im/app/chat/checkChat"
    }

    object Mall {
        const val GATEWAY = "gateway.do"
    }
} 