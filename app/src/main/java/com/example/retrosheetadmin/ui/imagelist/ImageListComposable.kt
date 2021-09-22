package com.example.retrosheetadmin.ui.imagelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun ImageListComposable(
    state: ImageListState,
    onTriggerEvent: (event: ImageListEvents) -> Unit
) {


    SwipeRefresh(
        state = rememberSwipeRefreshState(state is ImageListState.Loading),
        onRefresh = { onTriggerEvent(ImageListEvents.LoadImages) },
    ) {


        when (state) {

            is ImageListState.Success -> {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {


                    itemsIndexed(
                        items = state.imageList
                    ) { _, image ->
                        ImageCardComposable(
                            image = image,
                            onClickDeleteButton = {
                                onTriggerEvent(ImageListEvents.DeleteImage(image))
                            }
                        )
                    }

                }

            }


            is ImageListState.Error -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.errorMessage)
                    Spacer(modifier = Modifier.size(24.dp))
                    OutlinedButton(onClick = { onTriggerEvent(ImageListEvents.LoadImages) }) {
                        Text(text = "refresh")
                    }
                }

            }

            else -> {
                Box(modifier = Modifier.fillMaxSize())
            }

        }


    }

}