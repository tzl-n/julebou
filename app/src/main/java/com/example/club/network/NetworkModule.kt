package com.example.club.network

import com.example.club.BuildConfig
import com.example.club.network.interceptor.HttpHeaderInterceptor
import com.example.club.network.api.ApiConstants
import com.example.club.network.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * 日志拦截器
     * Debug 模式打印完整 Body，Release 只打印 Header（保护敏感信息）
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.HEADERS
        }

    /**
     * OkHttpClient
     * 拦截器顺序：Header -> Logging
     * Header 拦截器先执行，确保日志能看到注入后的完整请求头
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: HttpHeaderInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)   // 先注入 Header
            .addInterceptor(loggingInterceptor)  // 再打印日志
            .build()

    /** 主业务域名 Retrofit */
    @Provides
    @Singleton
    @Named("main")
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /** 商城 OpenAPI 域名 Retrofit */
    @Provides
    @Singleton
    @Named("mall")
    fun provideMallRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.MALL_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // ==================== Service 注入 ====================

    @Provides @Singleton
    fun provideAuthApiService(@Named("main") r: Retrofit): AuthApiService =
        r.create(AuthApiService::class.java)

    @Provides @Singleton
    fun provideMemberApiService(@Named("main") r: Retrofit): MemberApiService =
        r.create(MemberApiService::class.java)

    @Provides @Singleton
    fun providePostsApiService(@Named("main") r: Retrofit): PostsApiService =
        r.create(PostsApiService::class.java)

    @Provides @Singleton
    fun provideContentApiService(@Named("main") r: Retrofit): ContentApiService =
        r.create(ContentApiService::class.java)

    @Provides @Singleton
    fun provideClubApiService(@Named("main") r: Retrofit): ClubApiService =
        r.create(ClubApiService::class.java)

    @Provides @Singleton
    fun provideActivityApiService(@Named("main") r: Retrofit): ActivityApiService =
        r.create(ActivityApiService::class.java)

    @Provides @Singleton
    fun provideDiscoverApiService(@Named("main") r: Retrofit): DiscoverApiService =
        r.create(DiscoverApiService::class.java)

    @Provides @Singleton
    fun provideVehicleApiService(@Named("main") r: Retrofit): VehicleApiService =
        r.create(VehicleApiService::class.java)

    @Provides @Singleton
    fun provideMiscApiService(@Named("main") r: Retrofit): MiscApiService =
        r.create(MiscApiService::class.java)

    @Provides @Singleton
    fun provideImApiService(@Named("main") r: Retrofit): ImApiService =
        r.create(ImApiService::class.java)

    @Provides @Singleton
    fun provideMallApiService(@Named("mall") r: Retrofit): MallApiService =
        r.create(MallApiService::class.java)
}
