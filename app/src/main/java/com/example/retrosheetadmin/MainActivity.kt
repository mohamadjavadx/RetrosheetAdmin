package com.example.retrosheetadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.retrosheetadmin.datasource.network.ImageRepository
import com.example.retrosheetadmin.datasource.network.RetrosheetApiService
import com.example.retrosheetadmin.model.Image
import com.example.retrosheetadmin.ui.theme.RetrosheetAdminTheme
import com.example.retrosheetadmin.util.log
import com.example.retrosheetadmin.util.logSimple
import com.hadiyarajesh.flower.networkResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

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
                    Greeting("Android")
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