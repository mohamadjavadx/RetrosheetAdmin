package com.example.retrosheetadmin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("created_at")
    @Expose
    val createdAt: String,

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("link")
    @Expose
    val link: String,
)
