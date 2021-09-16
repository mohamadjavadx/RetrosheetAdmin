package com.example.retrosheetadmin.ui.imagelist

import com.example.retrosheetadmin.model.Image


data class ImageListState(
    val isLoading: Boolean,
    val imageList: List<Image>?,
    val errorMessage: String?
)