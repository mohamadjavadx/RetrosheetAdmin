package com.example.retrosheetadmin.datasource

import com.example.retrosheetadmin.datasource.network.RetrosheetApiService
import com.example.retrosheetadmin.datasource.network.requests.ImageRequest
import com.example.retrosheetadmin.model.Image
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
    private val retrosheetApiService: RetrosheetApiService
) {

    private fun List<Image>.removeInvalidItems(): List<Image> {
        val set = HashSet<String>()
        val list = ArrayList<Image>()
        for (e in this.asReversed()) {
            if (set.add(e.id) && !e.isArchived)
                list.add(e)
        }
        return list.reversed()
    }

    fun getAllImages(): Flow<Resource<List<Image>>> =
        networkResource(
            fetchFromRemote = {
                retrosheetApiService.getImages()
            }
        ).map {
            it.copy(data = it.data?.removeInvalidItems())
        }.flowOn(Dispatchers.IO)

    fun deleteImage(image: Image) =
        networkResource(
            fetchFromRemote = {
                retrosheetApiService.postImage(
                    ImageRequest(
                        id = image.id,
                        isArchived = true.toString(),
                        title = image.title,
                        link = image.link
                    )
                )
            }
        ).flowOn(Dispatchers.IO)

    fun editImage(image: Image) =
        networkResource(
            fetchFromRemote = {
                retrosheetApiService.postImage(
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