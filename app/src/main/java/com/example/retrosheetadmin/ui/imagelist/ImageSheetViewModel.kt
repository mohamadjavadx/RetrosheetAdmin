package com.example.retrosheetadmin.ui.imagelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrosheetadmin.datasource.ImageRepository
import com.example.retrosheetadmin.model.Image
import com.hadiyarajesh.flower.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageSheetViewModel
@Inject
constructor(
    private val imageRepository: ImageRepository,
) : ViewModel() {


    val state: MutableState<ImageListState> =
        mutableStateOf(ImageListState.Non)

    init {
        loadImages()
    }

    fun onTriggerEvent(event: ImageListEvents) {
        when (event) {
            is ImageListEvents.DeleteImage -> deleteImage(event.image)
            is ImageListEvents.EditImage -> updateImage(event.image)
            is ImageListEvents.LoadImages -> loadImages()
        }
    }

    private fun loadImages() {
        viewModelScope.launch {
            imageRepository.getAllImages().collect { dataState ->
                state.value = when (dataState.status) {
                    Resource.Status.SUCCESS -> ImageListState.Success(dataState.data!!)
                    Resource.Status.ERROR -> ImageListState.Error(dataState.message!!)
                    Resource.Status.LOADING -> ImageListState.Loading
                }
            }
        }
    }

    private fun deleteImage(image: Image) {
        viewModelScope.launch {
            imageRepository.deleteImage(image).collect { dataState ->
                state.value = when (dataState.status) {
                    Resource.Status.SUCCESS -> ImageListState.Non.also {
                        loadImages()
                    }
                    Resource.Status.ERROR -> ImageListState.Error(dataState.message!!)
                    Resource.Status.LOADING -> ImageListState.Loading
                }
            }
        }
    }

    private fun updateImage(image: Image) {
        viewModelScope.launch {
            imageRepository.updateImage(image).collect { dataState ->
                state.value = when (dataState.status) {
                    Resource.Status.SUCCESS -> ImageListState.Non.also {
                        loadImages()
                    }
                    Resource.Status.ERROR -> ImageListState.Error(dataState.message!!)
                    Resource.Status.LOADING -> ImageListState.Loading
                }
            }
        }
    }

}