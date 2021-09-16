package com.example.retrosheetadmin.ui.imagelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.retrosheetadmin.model.Image


@Composable
fun ImageCardComposable(
    image: Image,
    onClickDeleteButton: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {


        Column {


            Row {

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "title:")

                    Text(text = "link:")

                    Text(text = "createdAt:")
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = image.title)

                    Text(
                        text = image.link,
                        color = Color.Blue,
                        modifier = Modifier.clickable {

                        }
                    )

                    Text(text = image.createdAt)
                }

            }


            Button(onClick = { onClickDeleteButton.invoke() }) {
                Text(text = "delete")
            }

        }


    }
}