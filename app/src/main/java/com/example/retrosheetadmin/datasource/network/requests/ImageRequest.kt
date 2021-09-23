package com.example.retrosheetadmin.datasource.network.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageRequest(
    @Json(name = "id")
    override val id: String,

    @Json(name = "isArchived")
    override val isArchived: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "link")
    val link: String,
) : CommonRequest