package com.example.retrosheetadmin.datasource.network.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageRequest(
    @Json(name = "id")
    val id: String,

    @Json(name = "isArchived")
    val isArchived: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "link")
    val link: String,
)