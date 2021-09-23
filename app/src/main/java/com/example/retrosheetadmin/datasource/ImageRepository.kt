package com.example.retrosheetadmin.datasource

import com.example.retrosheetadmin.datasource.network.ImageApiService
import com.example.retrosheetadmin.datasource.network.requests.ImageRequest
import com.example.retrosheetadmin.model.Image
import com.example.retrosheetadmin.util.removeInvalidItems
import com.hadiyarajesh.flower.Resource
import com.hadiyarajesh.flower.networkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository
@Inject
constructor(
    private val imageApiService: ImageApiService
) {


    fun getAllImages(): Flow<Resource<List<Image>>> =
        networkResource(
            fetchFromRemote = {
                imageApiService.getAllImages()
            }
        ).map {
            it.copy(data = it.data?.removeInvalidItems())
        }.flowOn(Dispatchers.IO)


    fun deleteImage(image: Image) =
        networkResource(
            fetchFromRemote = {
                imageApiService.postImage(
                    ImageRequest(
                        id = image.id,
                        isArchived = true.toString(),
                        title = image.title,
                        link = image.link
                    )
                )
            }
        ).flowOn(Dispatchers.IO)


    fun updateImage(image: Image) =
        networkResource(
            fetchFromRemote = {
                imageApiService.postImage(
                    ImageRequest(
                        id = image.id,
                        isArchived = image.isArchived.toString(),
                        title = image.title,
                        link = image.link
                    )
                )
            }
        ).flowOn(Dispatchers.IO)

}