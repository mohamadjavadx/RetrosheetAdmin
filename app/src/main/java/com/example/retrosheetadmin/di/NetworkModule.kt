package com.example.retrosheetadmin.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.retrosheetadmin.*
import com.example.retrosheetadmin.datasource.network.RetrosheetApiService
import com.github.theapache64.retrosheet.RetrosheetInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hadiyarajesh.flower.calladpater.FlowCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson =
        GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        @ApplicationContext context: Context,
    ): Retrofit.Builder {

        val retrosheetInterceptor = RetrosheetInterceptor.Builder()
            .setLogging(false)
            // To Read
            .addSheet(
                sheetName = IMAGE_SHEET,
                IMAGE_SHEET_A, IMAGE_SHEET_B, IMAGE_SHEET_C, IMAGE_SHEET_D // columns in same order
            )
            // To write
//            .addForm(
//                ADD_NOTE_ENDPOINT,
//                "https://docs.google.com/forms/d/e/1FAIpQLSdmavg6P4eZTmIu-0M7xF_z-qDCHdpGebX8MGL43HSGAXcd3w/viewform?usp=sf_link" // form link
//            )
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
            .addConverterFactory(GsonConverterFactory.create(gson))

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