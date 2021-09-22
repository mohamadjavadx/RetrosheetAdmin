package com.example.retrosheetadmin.ui.imagelist

import com.example.retrosheetadmin.model.Image


//data class ImageListState(
//    val isLoading: Boolean,
//    val imageList: List<Image>?,
//    val errorMessage: String?
//)

sealed class ImageListState {
    object Loading : ImageListState()
    data class Success(val imageList: List<Image>) : ImageListState()
    data class Error(val errorMessage: String) : ImageListState()
    object Non : ImageListState()
}