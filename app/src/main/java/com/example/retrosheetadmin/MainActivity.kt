package com.example.retrosheetadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.retrosheetadmin.datasource.ImageRepository
import com.example.retrosheetadmin.ui.imagelist.ImageListComposable
import com.example.retrosheetadmin.ui.imagelist.ImageListEvents
import com.example.retrosheetadmin.ui.imagelist.ImageListState
import com.example.retrosheetadmin.ui.imagelist.ImageSheetViewModel
import com.example.retrosheetadmin.ui.theme.RetrosheetAdminTheme
import com.example.retrosheetadmin.util.log
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var imageRepository: ImageRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrosheetAdminTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    val viewModel: ImageSheetViewModel by viewModels()
                    val state : ImageListState = viewModel.state.value

                    ImageListComposable(
                        state,
                        onTriggerEvent = {
                            viewModel.onTriggerEvent(it)
                        }
                    )

//                    val state = viewModel.state.value
//                    if(state is ImageListState.Success)
//                    ImageListComposable(images = state.imageList.log()) {
//                        viewModel.onTriggerEvent(ImageListEvents.DeleteImage(it))
//                    }





//                    Column {
//
//                        when(val state = viewModel.state.value){
//
//                            is ImageListState.Loading -> {
//                                CircularProgressIndicator()
//
//                            }
//
//                            is ImageListState.Success -> {
//                                ImageListComposable(images = state.imageList.log()) {
//                                    viewModel.onTriggerEvent(ImageListEvents.DeleteImage(it))
//                                }
//                            }
//
//                            is ImageListState.Error -> {
//
//                            }
//
//                            !is ImageListState.Loading -> {
//                                Button(onClick = {
//                                    viewModel.onTriggerEvent(ImageListEvents.LoadImages)
//                                }) {
//                                    Text(text = "refresh")
//                                }
//                            }
//
//                            is ImageListState.Non -> Unit
//
//                        }
//
//                    }
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