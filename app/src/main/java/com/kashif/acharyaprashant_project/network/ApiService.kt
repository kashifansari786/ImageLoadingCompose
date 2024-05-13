package com.kashif.acharyaprashant_project.network

import com.kashif.acharyaprashant_project.models.ImagesModel
import com.kashif.acharyaprashant_project.models.ImagesModelItem
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */
interface ApiService {


    @GET("api/v2/content/misc/media-coverages")
    suspend fun getImagesList(@Query("limit") limit:Int): ImagesModel
}