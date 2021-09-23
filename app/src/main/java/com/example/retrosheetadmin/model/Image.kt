package com.example.retrosheetadmin.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    override val createdAt: String,
    override val id: String,
    override val isArchived: Boolean,
    val title: String,
    val link: String,
) : CommonItem