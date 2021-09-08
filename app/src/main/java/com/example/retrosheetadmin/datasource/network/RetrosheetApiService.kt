package com.example.retrosheetadmin.datasource.network

import com.example.retrosheetadmin.IMAGE_SHEET
import com.example.retrosheetadmin.model.Image
import com.github.theapache64.retrosheet.core.Read
import com.hadiyarajesh.flower.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrosheetApiService {

    @Read("SELECT * limit :limit")
    @GET(IMAGE_SHEET)
    fun getImages(
        @Query("limit") limit: Int
    ): Flow<ApiResponse<List<Image>?>>

}