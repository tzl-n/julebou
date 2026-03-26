package com.example.club.network

import com.example.club.BuildConfig
import com.example.club.network.api.ApiConstants
import com.example.club.network.interceptor.HttpHeaderInterceptor
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

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.HEADERS
        }

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
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides @Singleton @Named("main")
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides @Singleton @Named("mall")
    fun provideMallRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.MALL_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides @Singleton
    fun provideAuthApiService(@Named("main") r: Retrofit): AuthApiService = r.create(AuthApiService::class.java)
    @Provides @Singleton
    fun provideMemberApiService(@Named("main") r: Retrofit): MemberApiService = r.create(MemberApiService::class.java)
    @Provides @Singleton
    fun providePostsApiService(@Named("main") r: Retrofit): PostsApiService = r.create(PostsApiService::class.java)
    @Provides @Singleton
    fun provideContentApiService(@Named("main") r: Retrofit): ContentApiService = r.create(ContentApiService::class.java)
    @Provides @Singleton
    fun provideClubApiService(@Named("main") r: Retrofit): ClubApiService = r.create(ClubApiService::class.java)
    @Provides @Singleton
    fun provideActivityApiService(@Named("main") r: Retrofit): ActivityApiService = r.create(ActivityApiService::class.java)
    @Provides @Singleton
    fun provideDiscoverApiService(@Named("main") r: Retrofit): DiscoverApiService = r.create(DiscoverApiService::class.java)
    @Provides @Singleton
    fun provideVehicleApiService(@Named("main") r: Retrofit): VehicleApiService = r.create(VehicleApiService::class.java)
    @Provides @Singleton
    fun provideMiscApiService(@Named("main") r: Retrofit): MiscApiService = r.create(MiscApiService::class.java)
    @Provides @Singleton
    fun provideImApiService(@Named("main") r: Retrofit): ImApiService = r.create(ImApiService::class.java)
    @Provides @Singleton
    fun provideMallApiService(@Named("mall") r: Retrofit): MallApiService = r.create(MallApiService::class.java)
}
