package com.example.club.network.service

import com.example.club.network.api.ClubApiConstants
import retrofit2.http.*

/** 商城 OpenAPI - 所有请求统一 POST 到 gateway.do，通过 service 字段区分 */
interface MallApiService {

    @POST(ClubApiConstants.Mall.GATEWAY)
    suspend fun callGateway(
        @Body body: Map<String, @JvmSuppressWildcards Any>
    ): Any
}
