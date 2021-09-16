package com.example.retrosheetadmin.ui.imagelist

import com.example.retrosheetadmin.model.Image

sealed class ImageListEvents {

    object LoadImages: ImageListEvents()

    data class EditImage(val image: Image): ImageListEvents()

    data class DeleteImage(val image: Image): ImageListEvents()

}