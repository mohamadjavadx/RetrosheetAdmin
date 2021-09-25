package com.example.retrosheetadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.retrosheetadmin.datasource.ImageRepository
import com.example.retrosheetadmin.model.Image
import com.example.retrosheetadmin.ui.imagelist.ImageCardComposable
import com.example.retrosheetadmin.ui.imagelist.ImageListState
import com.example.retrosheetadmin.ui.imagelist.ImageSheetViewModel
import com.example.retrosheetadmin.ui.theme.RetrosheetAdminTheme
import com.example.retrosheetadmin.util.log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var imageRepository: ImageRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrosheetAdminTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {

                    val viewModel: ImageSheetViewModel by viewModels()
                    val state: ImageListState = viewModel.state.value

//                    ImageListComposable(
//                        state,
//                        onTriggerEvent = {
//                            viewModel.onTriggerEvent(it)
//                        }
//                    )

                    val list: ArrayList<Int> = arrayListOf(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                    )
                    var expandedItem by remember { mutableStateOf(-1) }

                    if (state is ImageListState.Success) {
                        val imageList: List<Image> = arrayListOf<Image>().apply {
                            addAll(state.imageList)
                            addAll(state.imageList)
                            addAll(state.imageList)
                            addAll(state.imageList)
                        }
                        LazyColumn {
                            itemsIndexed(imageList) { index: Int, item: Image ->
                                Spacer(modifier = Modifier.height(8.dp))
                                ImageCardComposable(
                                    image = item,
                                    index = index,
                                    isExpanded = expandedItem == index,
                                    onClickMore = { isExpanded ->
                                        expandedItem = if (isExpanded) index else -1
                                    },
                                    onClickDelete = {},
                                    onClickUpdate = {}
                                )
                            }
                        }
                    }


//                    ImageCardComposable(image = null, onClickDelete = {},onClickUpdate = {})

                }
            }
        }


        lifecycleScope.launchWhenCreated {
            imageRepository.getAllImages().collect {
                it.log()
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrosheetAdminTheme {
        Greeting("Android")
    }
}