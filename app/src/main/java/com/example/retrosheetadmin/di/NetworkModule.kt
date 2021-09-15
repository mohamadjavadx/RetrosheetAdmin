package com.example.retrosheetadmin.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.retrosheetadmin.datasource.network.RetrosheetApiService
import com.example.retrosheetadmin.util.*
import com.github.theapache64.retrosheet.RetrosheetInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hadiyarajesh.flower.calladpater.FlowCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson =
        GsonBuilder()
//            .excludeFieldsWithoutExposeAnnotation()
            .create()

    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi =
        Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        moshi:Moshi,
        @ApplicationContext context: Context,
    ): Retrofit.Builder {

        val retrosheetInterceptor = RetrosheetInterceptor.Builder()
            .setLogging(false)
            // To Read
            .addSheet(
                sheetName = IMAGE_SHEET,
                IMAGE_SHEET_A,
                IMAGE_SHEET_B,
                IMAGE_SHEET_C,
                IMAGE_SHEET_D,
                IMAGE_SHEET_E
            )
            // To write
            .addForm(
                ADD_IMAGE_ENDPOINT,
                ADD_IMAGE_FORM
            )
            .build()


        val chuckerInterceptor: ChuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .alwaysReadResponseBody(false)
            .build()

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(retrosheetInterceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))

    }


    @Singleton
    @Provides
    fun provideRetrosheetApiService(
        retrofit: Retrofit.Builder
    ): RetrosheetApiService {
        return retrofit
            .build()
            .create(RetrosheetApiService::class.java)
    }

}