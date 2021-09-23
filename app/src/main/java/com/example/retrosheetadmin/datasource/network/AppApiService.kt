package com.example.retrosheetadmin.datasource.network

import com.example.retrosheetadmin.datasource.network.requests.ImageRequest
import com.example.retrosheetadmin.model.Image
import com.example.retrosheetadmin.util.IMAGE_ENDPOINT
import com.example.retrosheetadmin.util.IMAGE_SHEET_NAME
import com.github.theapache64.retrosheet.core.Read
import com.github.theapache64.retrosheet.core.Write
import com.hadiyarajesh.flower.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppApiService {

    @Read("SELECT *")
    @GET(IMAGE_SHEET_NAME)
    fun getAllImages(): Flow<ApiResponse<List<Image>>>

    @Write
    @POST(IMAGE_ENDPOINT)
    fun postImage(@Body imageRequest: ImageRequest): Flow<ApiResponse<Unit>>

}