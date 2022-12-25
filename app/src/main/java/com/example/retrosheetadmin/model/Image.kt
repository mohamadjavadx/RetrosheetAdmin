package com.example.retrosheetadmin.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val createdAt: String,
    val id: String,
    val isArchived: Boolean,
    val title: String,
    val link: String,
)