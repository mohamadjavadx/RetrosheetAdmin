package com.example.retrosheetadmin.ui.imagelist

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.retrosheetadmin.model.Image
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.platform.LocalContext


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

                }

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = image.title)

                    Text(
                        text = image.link,
                        color = Color.Blue,
                        modifier = Modifier.clickable {

                        }
                    )

                }

            }

            Button(onClick = { onClickDeleteButton.invoke() }) {
                Text(text = "delete")
            }

        }

    }
}


@ExperimentalAnimationApi
@Composable
fun ImageCardComposable(
    image: Image,
    index: Int,
    isExpanded: Boolean,
    onClickMore: (isExpanded: Boolean) -> Unit,
    onClickDelete: () -> Unit,
    onClickUpdate: () -> Unit,
) {


    Card(
        modifier = Modifier
            .padding(top = 0.dp, bottom = 0.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium.copy(all = CornerSize(0.dp)),
        elevation = 4.dp

    ) {

        val current = LocalContext.current

        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {

            Row {

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f, true)
                ) {

                    Text(
                        text = "title: ${image.title}",
                        style = MaterialTheme.typography.subtitle1
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "link: ${image.link}",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.clickable {
                            val builder = CustomTabsIntent.Builder()
                            val customTabsIntent = builder.build()
                            customTabsIntent.launchUrl(current, Uri.parse(image.link))
                        }
                    )

                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    modifier = Modifier.align(Top),
                    onClick = { onClickMore(!isExpanded) }
                ) {
                    Crossfade(targetState = isExpanded) {
                        Icon(
                            imageVector = if (it) Icons.Rounded.Close else Icons.Rounded.MoreVert,
                            contentDescription = "",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                }

            }

            AnimatedVisibility(isExpanded) {

                Column {

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        OutlinedButton(
                            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface),
                            onClick = { onClickDelete() }
                        ) {
                            Text(text = "Delete")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedButton(
                            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface),
                            onClick = { onClickUpdate() }
                        ) {
                            Text(text = "Update")
                        }

                    }

                }

            }

        }

    }
}