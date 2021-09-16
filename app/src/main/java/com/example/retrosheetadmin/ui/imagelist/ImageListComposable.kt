package com.example.retrosheetadmin.ui.imagelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retrosheetadmin.model.Image


@Composable
fun ImageListComposable(
    images: List<Image>?,
    onClickDeleteButton: (image:Image) -> Unit
) {

    Box {

        if (!images.isNullOrEmpty()) {

            LazyColumn(modifier = Modifier.padding(8.dp)) {
                itemsIndexed(
                    items = images
                ) { _, image ->
                    ImageCardComposable(
                        image = image,
                        onClickDeleteButton = {
                            onClickDeleteButton.invoke(image)
                        }
                    )
                }
            }

        }

    }

}