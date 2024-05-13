package com.kashif.acharyaprashant_project.data

import com.kashif.acharyaprashant_project.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */
@Singleton
class ImagesDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getImagesList()=apiService.getImagesList(100)
}


