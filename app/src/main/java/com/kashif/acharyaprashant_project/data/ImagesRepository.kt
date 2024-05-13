package com.kashif.acharyaprashant_project.data


import com.kashif.acharyaprashant_project.models.ImagesModelItem
import com.kashif.acharyaprashant_project.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */
@Singleton
class ImagesRepository @Inject constructor(private val imagesDataSource: ImagesDataSource) {

    suspend fun getImageList():Resource<ArrayList<ImagesModelItem>>{
        return try{
            Resource.Success(data = imagesDataSource.getImagesList())
        }catch (e:Exception){
            Resource.Error(message = e.message.toString())
        }
    }

}