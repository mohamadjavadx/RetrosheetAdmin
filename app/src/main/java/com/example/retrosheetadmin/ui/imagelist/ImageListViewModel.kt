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
class ImageListViewModel
@Inject
constructor(
    private val imageRepository: ImageRepository,
) : ViewModel() {


    val state: MutableState<ImageListState> =
        mutableStateOf(
            ImageListState(
                isLoading = false,
                imageList = null,
                errorMessage = null
            )
        )


    init {
        loadImages()
    }

    fun onTriggerEvent(event: ImageListEvents) {
        when (event) {
            is ImageListEvents.DeleteImage -> deleteImage(event.image)
            is ImageListEvents.EditImage -> editImage(event.image)
            is ImageListEvents.LoadImages -> loadImages()
        }
    }

    private fun loadImages() {
        viewModelScope.launch {
            imageRepository.getAllImages().collect { dataState ->
                state.value = state.value.copy(
                    isLoading = dataState.status == Resource.Status.LOADING,
                    imageList = dataState.data,
                    errorMessage = dataState.message
                )
            }
        }
    }

    private fun deleteImage(image: Image) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            imageRepository.deleteImage(image).collect { dataState ->
                state.value = state.value.copy(
                    isLoading = dataState.status == Resource.Status.LOADING,
                    errorMessage = dataState.message
                )
                if (dataState.status == Resource.Status.SUCCESS) {
                    loadImages()
                }
            }
        }
    }

    private fun editImage(image: Image) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            imageRepository.editImage(image).collect { dataState ->
                state.value = state.value.copy(
                    isLoading = dataState.status == Resource.Status.LOADING,
                    errorMessage = dataState.message
                )
                if (dataState.status == Resource.Status.SUCCESS) {
                    loadImages()
                }
            }
        }
    }

}