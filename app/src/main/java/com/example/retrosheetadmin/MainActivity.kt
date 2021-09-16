package com.example.retrosheetadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.retrosheetadmin.datasource.ImageRepository
import com.example.retrosheetadmin.ui.imagelist.ImageListComposable
import com.example.retrosheetadmin.ui.imagelist.ImageListEvents
import com.example.retrosheetadmin.ui.imagelist.ImageListViewModel
import com.example.retrosheetadmin.ui.theme.RetrosheetAdminTheme
import com.example.retrosheetadmin.util.log
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

                    Column {


                        val viewModel: ImageListViewModel by viewModels<ImageListViewModel>()

                        if (viewModel.state.value.isLoading)
                            CircularProgressIndicator()

                        if (!viewModel.state.value.isLoading)
                            Button(onClick = {
                                viewModel.onTriggerEvent(ImageListEvents.LoadImages)
                            }) {
                                Text(text = "refresh")
                            }

                        if (!viewModel.state.value.isLoading)
                            ImageListComposable(images = viewModel.state.value.imageList) {
                                viewModel.onTriggerEvent(ImageListEvents.DeleteImage(it))
                            }

                    }
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