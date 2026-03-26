package com.example.club.network

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

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

    @Provides
    @Singleton
    @Named("main")
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("mall")
    fun provideMallRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConstants.MALL_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // ---- Services ----

    @Provides
    @Singleton
    fun provideAuthApiService(@Named("main") retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideMemberApiService(@Named("main") retrofit: Retrofit): MemberApiService =
        retrofit.create(MemberApiService::class.java)

    @Provides
    @Singleton
    fun providePostsApiService(@Named("main") retrofit: Retrofit): PostsApiService =
        retrofit.create(PostsApiService::class.java)

    @Provides
    @Singleton
    fun provideContentApiService(@Named("main") retrofit: Retrofit): ContentApiService =
        retrofit.create(ContentApiService::class.java)

    @Provides
    @Singleton
    fun provideClubApiService(@Named("main") retrofit: Retrofit): ClubApiService =
        retrofit.create(ClubApiService::class.java)

    @Provides
    @Singleton
    fun provideActivityApiService(@Named("main") retrofit: Retrofit): ActivityApiService =
        retrofit.create(ActivityApiService::class.java)

    @Provides
    @Singleton
    fun provideDiscoverApiService(@Named("main") retrofit: Retrofit): DiscoverApiService =
        retrofit.create(DiscoverApiService::class.java)

    @Provides
    @Singleton
    fun provideVehicleApiService(@Named("main") retrofit: Retrofit): VehicleApiService =
        retrofit.create(VehicleApiService::class.java)

    @Provides
    @Singleton
    fun provideMiscApiService(@Named("main") retrofit: Retrofit): MiscApiService =
        retrofit.create(MiscApiService::class.java)

    @Provides
    @Singleton
    fun provideImApiService(@Named("main") retrofit: Retrofit): ImApiService =
        retrofit.create(ImApiService::class.java)

    @Provides
    @Singleton
    fun provideMallApiService(@Named("mall") retrofit: Retrofit): MallApiService =
        retrofit.create(MallApiService::class.java)
}
